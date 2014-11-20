'''
Created on 2014-5-8

@author:  Furong Peng
'''

# from crawler import DownloadPages,moviePath
#  

from crawler.dldPages import DownloadPages
import os 
def moviePath(mid,out_dir):
    url='http://movie.douban.com/subject/%s/' % mid
    file_name= os.path.join( out_dir, 'subject', mid+'.html')
    return (url,file_name)


def DownloadMovie(job_file,log_file,out_dir,time_interval=1.5,error_times= 20):
    dlm = DownloadPages(job_file,log_file,out_dir,time_interval=time_interval
                        ,error_times= error_times,path_fun=moviePath)
    dlm.run()