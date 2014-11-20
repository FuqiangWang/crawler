var db = connect("localhost:27017/douban");
var cur = db.celebrity.find();
var genres = new Array();
var mappings = {};
while(cur.hasNext()){
    var item = cur.next();
    if(item.hasOwnProperty('occupation'))
    	{
    		var occpuations = item['occupation'].trim().split('/');
    		for(var i=0; i<occpuations.length;++i){
    			var occp = occpuations[i].trim();
    			if (!(occp in mappings)){
        			mappings[occp] = 1;
        		}
        		else{
        			mappings[occp] = mappings[occp] + 1 ;
        		}
    		}
    		
    	
    	}
}

var keys = [];
for(var key in mappings)
{
	keys.push(key);
}
for(var i =0; i<keys.length; ++i){
	print(i+'\t'+keys[i]+'\t'+mappings[keys[i]]);	
}


