var db = connect("localhost:27017/douban");
var cur = db.celebrity.find();
var genres = new Array();
var mappings = {};
while(cur.hasNext()){
    var item = cur.next();
    if(item.hasOwnProperty('sign'))
    	{
    		var sign = item['sign'].trim();
    		if (!(sign in mappings)){
    			mappings[sign] = 1;
    		}
    		else{
    			mappings[sign] = mappings[sign] + 1 ;
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


