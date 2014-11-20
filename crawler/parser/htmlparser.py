'''
Created on 2014-4-30

@author:  Furong Peng
'''

from bs4 import BeautifulSoup
# pip intall beautifulsoup4

import copy
import re
#from bs4 import BeautifulStoneSoup

prefix = 'var_'

def ParseTemplate(templateString):    
    return BeautifulSoup(templateString,'xml');

def ParseHtml(template,htmlString):
    return ParseElement(BeautifulSoup(htmlString),template)


def GetKeyWord(key_str):
    ''' Check if the input is the predefined keyword
        if key_str is keyword then return the variable name.
        otherwise return None
    '''
    key_str = key_str.strip()
    if len(key_str) >2 :
        if key_str[0] == '$' and key_str[-1] == '$':
            return key_str[1:-1]
    return None

 
def NodeArrayName(xmlEle):
    ''' Check if the element is the array node.
        If element is an array then return the array name.
        otherwise return None
    '''
    if xmlEle.attrs.has_key(prefix+'array'):
        return xmlEle.attrs[prefix+'array'].replace('$','')
    else:
        return None
def NodeRawName(xmlEle):
    ''' Check if the node is raw node. 
        If the node is defined as raw node then return the variable name
        otherwise return None
    '''
    if xmlEle.attrs.has_key(prefix+'raw'):
        return xmlEle.attrs[prefix+'raw'].replace('$','')
    else:
        return None
def NodeText(xmlEle):
    ele_str = copy.deepcopy(str(xmlEle))
    children = xmlEle.findChildren(recursive=False)
    for child in children:
        child_str = str(child)
        ele_str = ele_str.replace(child_str, '')
    tokens = re.findall(r'<[\\b]*'+xmlEle.name+'[^>]*>(.*)<[\\b]*/[\\b]*'+xmlEle.name+'[\\b]*>',ele_str,re.S)
    if len(tokens)==0 :
        ele_str = ''
    else:
        ele_str = tokens[0]
    return ele_str.strip()
    
def NodeKeys(xmlEle):
    '''
    Parse the current xmlEle to find the search condition. tag name and attributes.
    only the leaf node's text is considered as the search condition
    '''
    key_fields  = dict() # used to store the fields should be extracted
    attr = copy.deepcopy(xmlEle.attrs) 
    text = NodeText(xmlEle)
    name = xmlEle.name  # tag name which is a key condition
    keyw = GetKeyWord(text) # Check if the text is the keywords
    
    recursive = True  # check if we need recursive search the node. default is recursive search
    if attr.has_key(prefix+'recursive'):
        recursive = attr[prefix+'recursive'].lower()=='true'
        
    
    for k in attr.keys(): # ignore the reserved attributes (we defined special attributes)
        if k.find(prefix)==0:
            del attr[k]
    if keyw != None: # text is the key words
        key_fields[keyw] = 'text'
        text = None
    for k in attr.keys(): # key in the attributes
        keyw = GetKeyWord(attr[k])
        if keyw != None: 
            key_fields[keyw] = k
            del attr[k]
    return name, attr,text, key_fields,recursive


def ExtractField(Node,key_fields):
    '''  According to the key_fields to extract the information in the Node (which is form html)
         return the extracted key fields as a dictionary.
    '''
    kf = copy.deepcopy(key_fields) # the extracted dictionary will be returned
    for k in kf.keys():
        if kf[k]=='text': # specially process the text attribute.
            kf[k] = NodeText(Node)
        elif Node.attrs.has_key(kf[k]): # extract the key fields from attributes
            kf[k] = Node.attrs[kf[k]]
        else:
            kf[k] = None # didnot found any field in this node
    return kf

def ParseElement(htmlElements, xmlEle, arry_name = None):
    '''  htmlElements :  the element subtree of the html node
         xmlEle :       the xml node for searching html node
         arry_name: if it is None just return a single node. 
                    If given as a name (sting type) then all found elements are composed as a array
                    to return whose name is 'array_name' saved in dict 
         
    '''
    dict_rtn = dict() # store the returned dictory
    next_arry_name = NodeArrayName(xmlEle)  # Check if the sub nodes is composed to a array
    raw_name = NodeRawName(xmlEle) # if node has raw-name then no need to parse
    
    if(xmlEle.name=='[document]'): #  if current node is the the root
        elements = [htmlElements] # if current node is root then just go down the tree.
        key_fields = {}
    else:
        '''Extract the xml node name, attr for condition.
           key_fields is the fields should be extracted
           recursive implies whether rescursively search the node.
        '''
        name, attr,text, key_fields,recursive = NodeKeys(xmlEle)
        elements = htmlElements.find_all(name,attr,recursive,text)
        
        if raw_name != None : # do not need to parse 
            return {raw_name: elements}
        
    dic_arry = list() # store the found dictionary for each element
    
    # recursively extract key fields information form found elements
    for ele in elements:
        kf = ExtractField(ele,key_fields)
        for n_c in xrange(len(xmlEle.contents)): # recursively extracting
            if xmlEle.contents[n_c].name == None:
                continue
            child_dic = ParseElement(ele, xmlEle.contents[n_c], next_arry_name)
            kf = dict(child_dic.items()+kf.items())
        dic_arry.append(kf)
        if arry_name == None:  # no need to return all elements
            break;
        
    if arry_name == None:
        if len(dic_arry) >0: dict_rtn= dic_arry[0]
    else:
        dict_rtn = {arry_name:dic_arry}
    return dict_rtn


if __name__ == '__main__':
    template_file = 'movie.xml'
    html_file = 'movie.html'
    template = []
    with open(template_file,'r') as fin, open(html_file,'r') as fhtml:
        template = ParseTemplate(fin.read())# save template from file once for crawling
        html_string = fhtml.read()
        TD =  ParseHtml(template,html_string)
        print TD
    
    