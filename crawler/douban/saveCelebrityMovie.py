'''
Created on 2014-5-12

@author: Furong Peng
'''
from crawler import Base
from crawler.parser import ParseTemplate, ParseHtml
import urllib2
import pymongo
import sys
import time

class SaveCelebrityMovie(Base):
    '''
    classdocs
    '''
    headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0' }
    conn = None 
    db_douban = None
    collection_cm = None  # celebrity_movie
    def __init__(self, job_file,log_file,template_file,error_times= 20,time_interval = 0):
        '''
        Constructor
        '''
        Base.__init__(self, job_file, log_file,error_times,time_interval)
        
        with open(template_file,'r') as ftpl:
            self.xml_tplt = ParseTemplate(ftpl.read())
        
    def CelebrityMovies(self, celebrity_id):
        movies = list()
        url_base = 'http://movie.douban.com/celebrity/%s/movies' % celebrity_id
        nextPage = '?sortby=vote&format=pic&'
        finished = False
        time_interval = 1.5 # seconds
        while not finished:
            time1 = time.time()
            url = url_base + nextPage
            req=urllib2.Request(url,headers=self.headers)
            response=urllib2.urlopen(req)
            html_str=response.read()
            page_info = ParseHtml(self.xml_tplt,html_str)
            
            if page_info.has_key('nextPage'):
                nextPage = page_info['nextPage']
            else:
                finished = True
            if page_info.has_key('movie_array'):
                movie_array = page_info['movie_array']
                for mv in movie_array:
                    if mv.has_key('url') and \
                       mv.has_key('comments') and \
                       len(mv['comments'])==3:
                        murl = mv['url']
                        movie_id = murl.replace('http://movie.douban.com/subject/','')
                        movie_id = movie_id.replace('/','')
                        movies.append(movie_id)

                    
            else:
                finished = True
            # sleep
            time_span = time.time() - time1
            if(time_span<time_interval):
                time.sleep(time_interval - time_span)
        return movies        
    
    def _doJob(self,job):
        try:
           
            movies = self.CelebrityMovies(job)            
            if len(movies) == 0:
                return None
            '''Save data to mongo db'''
            cm = dict()
            cm['celebrity_id'] = job
            cm['movies'] = movies
            self.collection_cm.insert(cm)
            
        except:
            print "Unexpected error:", sys.exc_info()[0], 'Job: ',job
            return None
        return job
    
    def run(self):
        self.conn =pymongo.Connection('localhost', 27017)
        self.db_douban = self.conn['douban']    
        self.collection_cm = self.db_douban['celebrity_movie']
        rst = Base.run(self)
        self.conn.close()
        return rst
    
if __name__ =="__main__":
    with open('cele_movie.html','r') as fhtml, open('cele_movie.xml','r') as fxml:
        tpl = ParseTemplate(fxml.read())
        token = ParseHtml(tpl,fhtml.read())
        print token
    pass  