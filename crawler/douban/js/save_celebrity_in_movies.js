var db = connect("localhost:27017/douban");
var cur = db.movie.find({star:{$exists: true}},{'star.url' : 1});

while(cur.hasNext()){
    var item = cur.next();
    for(var i =0; i<item['star'].length; ++i){
        var url = item['star'][i].url;
        if(url == undefined)continue;
        if(url.indexOf('/celebrity/')==0){
            print( url.substring(11).replace('/','') );
        }
     }

}

