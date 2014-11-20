from crawler.dldPages import DownloadPages
import os 

def shopPath(mid,out_dir):
    url='http://www.dianping.com/shop/%s/' % mid
    file_name= os.path.join( out_dir, 'shop', mid+'.html')
    return (url,file_name)


def DownloadShops(job_file,log_file,out_dir,time_interval=1.5,error_times= 20):
    dlm = DownloadPages(job_file,log_file,out_dir,time_interval=time_interval
                        ,error_times= error_times,path_fun=shopPath)
    dlm.run()