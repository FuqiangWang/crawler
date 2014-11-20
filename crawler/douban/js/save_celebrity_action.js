//# mongo --quiet celebrity_action.js > celebrity_action.txt

var db = connect("localhost:27017/douban");
var cur = db.celebrity_movie.find();

while(cur.hasNext()){
    var item = cur.next();
    if(item['movies'].length<10){
    	continue;
    }
    
    var str_print = item['celebrity_id']
    for(var i =0; i<item['movies'].length; ++i){
    	str_print = str_print + '\t' +item['movies'][i];
        }
    print(str_print);
     
}


