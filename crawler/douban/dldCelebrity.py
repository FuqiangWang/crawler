'''
Created on 2014-5-8

@author:  Furong Peng
'''

from crawler.dldPages import DownloadPages
import os 


def celebrityPath(mid,out_dir):
    url='http://movie.douban.com/celebrity/%s/' % mid
    file_name= os.path.join( out_dir,'celebrity',
                                     mid+'.html')
    return (url,file_name)




def DownloadCelebrity(job_file,log_file,out_dir):
    dlm = DownloadPages(job_file,log_file,out_dir,time_interval=1.5,error_times= 20,path_fun=celebrityPath)
    dlm.run()