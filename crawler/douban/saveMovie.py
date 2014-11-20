'''
Created on 2014-5-5

@author: Furong Peng
'''
import sys
from crawler import Base
from crawler.parser import ParseTemplate,ParseHtml
from os.path import join
import re

import  pymongo

mapping = {u'\u5bfc\u6f14':'director',
           u'\u4e3b\u6f14':'star',
           u'\u7f16\u5267':'editor',
           u'\u7c7b\u578b':'genre',
           u'\u5236\u7247\u56fd\u5bb6\u002f\u5730\u533a':'location',
           u'\u8bed\u8a00':'language',
           u'\u4e0a\u6620\u65e5\u671f':'release_date',
           u'\u7247\u957f':'runtime',
           u'\u53c8\u540d':'alias',
           u'\u0049\u004d\u0044\u0062\u94fe\u63a5':'imdb_link',
           u'\u5b98\u65b9\u7f51\u7ad9':'official_site',
           u'\u5b98\u65b9\u5c0f\u7ad9':'douban_site'}


def extrace_Url_Text(token):
    url_pattern = re.compile(r'<a .*?href="(.*?)".*?>')
    html_mark_pattern = re.compile(r'<[^>]+>')
    
    url = url_pattern.findall(token)
    if len(url)>0:
        url = url[0]
    else:
        url = None
    token = html_mark_pattern.sub('',token)
    token = token.replace(':','')
    return {'url':url,'value':token}
    
    
    

def extractFields(line):
    key_pattern = re.compile(r'(?<=<span class=\"pl\">)[^<]+(?=\:?</span>)')
    tokens = key_pattern.findall(line)
    if len(tokens) != 1:
        return None
    key = tokens[0]
    key = key.replace(':','')
    key = key.decode('utf-8') 
    
    if not mapping.has_key(key):
        return None
    key = mapping[key]
    
    tokens = key_pattern.split(line)
    if len(tokens) != 2:
        return None
    line = tokens[1]
    
    value_array = list()
    values_pattern = re.compile(r'(?<=[\W^/>])/(?=[\W^/<])')
    tokens = values_pattern.split(line)
    for t in tokens:
        value_array.append(extrace_Url_Text(t))
    return {key:value_array}
    
def extractinfo(info_str,mid):
    info_str = info_str.replace('v:','')
    movie = {'movie_id':mid}
    tokens = info_str.split('<br/>')
    for t in tokens:
        p = extractFields(t)
        if p!= None :
            movie = dict(movie.items() + p.items())
    return movie



class SaveMovie(Base):
    '''
    classdocs
    '''
    
    conn = None 
    db_douban = None
    collection_movie = None
    def __init__(self, job_file,log_file,data_dir,template_file,error_times=100):
        '''
        Constructor
        '''
        Base.__init__(self, job_file, log_file,error_times=error_times)
        self.data_dir = data_dir
        
        with open(template_file,'r') as ftpl:
            self.xml_tplt = ParseTemplate(ftpl.read())
        
            
    
    def _doJob(self,job,logFile):
        try:
            fileName = join(self.data_dir,'subject',"%s.html"%job)
            with open(fileName,'r') as fin:
                html_str = fin.read()
                movie_info = ParseHtml(self.xml_tplt,html_str)
                if movie_info == None:
                    return None
                movie = extractinfo(str(movie_info['Info'][0]),job)
                if movie != None:
                    movie['title'] = movie_info['title']
                    self.collection_movie.insert(movie)
        except:
            print "Unexpected error:", sys.exc_info()[0], 'Job: ',job
            return None
        return job
    
    def run(self):
        self.conn =pymongo.Connection('localhost', 27017)
        self.db_douban = self.conn['douban']    
        self.collection_movie = self.db_douban['movie']    
        self.collection_movie.ensure_index("movie_id")
        rst = Base.run(self)
        self.conn.close()
        return rst
    
if __name__ =="__main__":
    with open('movie.html','r') as fhtml, open('movie.xml','r') as fxml:
        tpl = ParseTemplate(fxml.read())
        token = ParseHtml(tpl,fhtml.read())
        print token
    pass 