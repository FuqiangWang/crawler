'''
Created on 2014-5-5

@author: Furong Peng
'''
from base import Base,getUrl
import sys,time
from eta import ETA
def pagePath(mid,out_dir):
    raise NotImplementedError()
    url = '';
    path = ''; 
    return (url,path)




class DownloadPages(Base):
    '''
         Download Movies
    '''
    
    def __init__(self, job_file,log_file, out_dir,time_interval=1.5,error_times= 20,path_fun = pagePath):
        Base.__init__(self, job_file, log_file, time_interval=time_interval,error_times = error_times)
        self.out_dir = out_dir
        self.path_fun = path_fun
        
        '''
        Constructor
        '''
    
    def _doJob(self, job,logFile = None):
        try:
            (url,file_name) = self.path_fun(job,self.out_dir)
            
            html=getUrl(url)
            with open(file_name,'w') as f_html:
                f_html.write(html)
            return job
        except:
            print "Unexpected error:", sys.exc_info()[0],'Job: %s'%job
            return None
    def _schedualJobs(self,jobs):
        ''' strategy for jobs
        '''
        time_interval = self.time_interval
        with open(self.log_file,'a') as logFile:
            n_err = 0
            eta = ETA(len(jobs))
            for i in xrange(len(jobs)):
                eta.print_status(i)
                time1 = time.time()
                result =self._doJob(jobs[i],logFile) 
                if(result != None  ):
                    self._saveLog(logFile,jobs[i],success=True)
                    self._collect(result)
                    if self.result_file!=None :
                        self._saveResult()  
                    time_interval = time_interval/4
                    if time_interval < self.time_interval:
                        time_interval = self.time_interval               
                else:
                    self._saveLog(logFile,jobs[i],success=False)
                    n_err += 1
                    time_interval = time_interval*2
                if n_err == self.error_times:
                    break;
                time_span = time.time() - time1
                if(time_span<time_interval):
                    time.sleep(time_interval - time_span)
            eta.done()
        