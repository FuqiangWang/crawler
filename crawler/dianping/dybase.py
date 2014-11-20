'''
Created on 2014-5-18

@author: Furong Peng
'''

from crawler import Base
from eta import ETA
import time

class DynamicBase(Base):
    '''
    classdocs
    '''


    def __init__(self, job_file,log_file, error_times= 3, time_interval = 0,result_file=None):
        Base.__init__(self,job_file,log_file, error_times= error_times, 
                      time_interval = time_interval,result_file=result_file)
        '''
        Constructor
        '''
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
                if(time_span<self.time_interval):
                    time.sleep(self.time_interval - time_span)
            eta.done()
        