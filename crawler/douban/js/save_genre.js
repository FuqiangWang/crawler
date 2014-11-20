var db = connect("localhost:27017/douban");
var cur = db.movie.find({'genre':{$exists:true}},{'movie_id' : 1,'title' : 1,'genre' : 1});
var genres = {};
var mapping = {};
mapping['喜劇'] = '喜剧';
mapping["喜劇 Comedy"] = '喜剧';
mapping['惊栗'] = '惊悚';
mapping['Drama'] = '剧情';
mapping['劇情 Drama'] = '剧情';
mapping['懸疑 Mystery'] = '悬疑';
mapping['悬念'] = '悬疑';
mapping['Comedy'] = '动画';
mapping['War'] = '战争';
mapping['Western'] = '西部';
mapping['愛情 Romance'] = '爱情';
mapping['Romance'] = '爱情';
mapping['History'] = '历史';
while(cur.hasNext()){
    var item = cur.next();
    if(item.hasOwnProperty('genre'))
    	{
    		var gens = item['genre'];
    		for(var i=0; i<gens.length;++i){
    			   var g = gens[i].value.trim();
    			   if (g in mapping){
    				   g = mapping[g]; 
    			   }    				    
    				  
    			   if (!(g in genres))
    			    {
    				   genres[g] = 1;
    			    }
    			   else{
    				   genres[g] = genres[g]+1;
    			   }
    			   
    			}
    	
    	}
}


for(var g in genres)
	{
	    print(g+"\t"+genres[g]);
	    //print("\""+genres[i].trim()+"\",");
	}

