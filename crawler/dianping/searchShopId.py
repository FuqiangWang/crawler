'''
Created on 2014-5-16

@author: Furong Peng
'''
from crawler import Base
import sys
from crawler.parser import ParseTemplate,ParseHtml
import urllib2,time

class SearchShopId(Base):
    '''
    classdocs
    '''
    headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0' }

    def __init__(self, job_file,log_file,template_file,shop_id_file, error_times= 3, time_interval = 1.5):
        
        Base.__init__(self,job_file,log_file,error_times,time_interval)
        self.shop_id_file_name = shop_id_file
        
        with open(template_file,'r') as ftpl:
            self.xml_tplt = ParseTemplate(ftpl.read())
        
        '''
        Constructor
        '''
    def run(self):
        self.shop_id_file = open(self.shop_id_file_name,'a')
        result = Base.run(self)
        self.shop_id_file.close()
        return result
    
    def _doJob(self,job,logFile = None):
        ''' do the jobs 
        '''
        result = None
        url = job
        url_base = 'http://www.dianping.com'
        time_interval = 1.5 # seconds
        try:            
            Finished = False
            
            while not Finished:
                time1 = time.time()
                req=urllib2.Request(url,headers=self.headers)
                response=urllib2.urlopen(req)
                html_str=response.read()
                shop = ParseHtml(self.xml_tplt,html_str)
                
                '''Extract the shod id list'''
                
                shop_id = [shop_url['shop'].replace('/shop/','') for shop_url in shop['shoplist']]
                self.shop_id_file.write('\n'.join(shop_id)+'\n')

                if result == None:
                    result = job
                else:
                    self._saveLog(logFile, url, True)                  
                
                if 'nextPage' not in shop :
                    Finished = True
                else:
                    url = url_base + shop['nextPage']
                
                # sleep
                time_span = time.time() - time1
                if(time_span<time_interval):
                    time.sleep(time_interval - time_span)
                    
        except:
            print "Unexpected error:", sys.exc_info()[0], 'Job: ',url
            if result != None :
                self._saveLog(logFile, url, False)
        
        return result    
    
    def _saveLog(self,logFile,job,success=True):
        '''
             Save log for success job
        '''
        logFile.write('%s\t%d\n'%(job,success ))
        logFile.flush()
           
    def _resumeJob(self,jobs,logs):
        '''  skip the saved jobs
        '''
        logs = [l.split('\t') for l in logs]
        logs = dict(logs)
        
        jobs = list(set(jobs) or set(logs.keys()))
        
        jobs = [job for job in jobs if job not in logs or bool(logs[job])]
        
        return jobs


if __name__ == '__main__':
    with open('searchShopid.html','r') as fhtml, open('searchShopid.xml','r') as fxml:
        tpl = ParseTemplate(fxml.read())
        token = ParseHtml(tpl,fhtml.read())
        print token
    pass  
    