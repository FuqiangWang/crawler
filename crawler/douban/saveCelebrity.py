'''
Created on 2014-5-12

@author:  Furong Peng
'''


import sys
from crawler import Base
from crawler.parser import ParseTemplate,ParseHtml
from os.path import join


import  pymongo

mapping = {u'\u6027\u522b':'gender',
           u'\u661f\u5ea7':'sign',
           u'\u51fa\u751f\u65e5\u671f':'birth_place',
           u'\u51fa\u751f\u5730':'birth_date',
           u'\u804c\u4e1a':'occupation'}

  
def extractinfo(info_array,cele_d):
    celebrity = {'celebrity_id':cele_d}
    for t in info_array:
        key = t['key'].decode('utf-8') 
        if mapping.has_key(key):
            key = mapping[key]
            value = t['value']
            value = value.replace(':','')
            celebrity[key] = value.strip()
    return celebrity



class SaveCelebrity(Base):
    '''
    classdocs
    '''
    
    conn = None 
    db_douban = None
    collection_celebrity = None
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
            fileName = join(self.data_dir,'celebrity',"%s.html"%job)
            with open(fileName,'r') as fin:
                html_str = fin.read()
                celebrity_info = ParseHtml(self.xml_tplt,html_str)
                if celebrity_info == None:
                    return None
                celebrity = extractinfo(celebrity_info['Info'],job)
                if celebrity != None:
                    celebrity['name'] = celebrity_info['name']
                    self.collection_celebrity.insert(celebrity)
        except:
            print " Unexpected error:", sys.exc_info()[0], ' Job: ',job
            return None
        return job
    
    def run(self):
        self.conn =pymongo.Connection('localhost', 27017)
        self.db_douban = self.conn['douban']    
        self.collection_celebrity = self.db_douban['celebrity']    
        self.collection_celebrity.ensure_index("celebrity_id")
        rst = Base.run(self)
        self.conn.close()
        return rst
    
        
        
if __name__ =="__main__":
    with open('celebrity.html','r') as fhtml, open('celebrity.xml','r') as fxml:
        tpl = ParseTemplate(fxml.read())
        token = ParseHtml(tpl,fhtml.read())
        print token
    pass        