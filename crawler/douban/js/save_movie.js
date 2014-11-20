
var genre = ['剧情','喜剧','爱情','动作','惊悚','犯罪','动画','悬疑',
             '冒险','恐怖','家庭','短片','科幻','奇幻','纪录片','战争',
             '历史','音乐','传记','歌舞','西部','运动','古装','同性',
             '武侠','儿童','黑色电影','舞台艺术','情色','鬼怪','灾难'];

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




var db = connect("localhost:27017/douban");
var cur = db.movie.find({'genre':{$exists:true}},{'movie_id' : 1,'title' : 1,'genre' : 1});

while(cur.hasNext()){
    var item = cur.next();
    if(item.hasOwnProperty('genre'))
    	{
    	    var movie_str = item['movie_id'];
    	    //movie_str = '\t' + movie_str+item['title'].replace('\t',' ');
    		var gens = item['genre'];
    		var has_genre = false;
    		var vec = new Array(genre.length);
    		for(var i=0; i<vec.length; ++i){
    			vec[i] = 0;
    		}
    		
    		for(var i=0; i<gens.length;++i){
    			var g = gens[i].value.trim();
    			if(g in mapping){
    				g = mapping[g];
    			}
    			if(genre.indexOf(g)!=-1){
    				has_genre = true;
    				vec[genre.indexOf(g)] = 1;
    			}
    			
    		}
    		if(has_genre){
    			movie_str = movie_str + '\t' + vec.join('\t');
    			print(movie_str);
    		}
    		
    	
    	}
}


