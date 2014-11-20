/*
var countries=['美国','日本','中国','英国','韩国','法国','加拿大',
               '香港','台湾','德国','意大利','西班牙','澳大利亚','印度','瑞典']
*/

/*
var signs=['狮子座','天蝎座','双子座','摩羯座','水瓶座','射手座',
          '双鱼座','白羊座','天秤座','巨蟹座','处女座','金牛座'];
*/

/*
var occupations=['演员','制片','导演','编剧','配音','副导演','摄影','视觉特效',
                 '选角导演','剪辑','作曲','艺术指导','服装设计',
                 '美术设计','动作指导','化妆师','布景师'];
*/
var occupations=['演员','制片','导演','编剧','配音'];

var countries=['美国','日本','中国','英国','韩国','法国','加拿大',
               '香港','台湾','德国']


function filterOccupation(occ){
	var vec = new Array(occupations.length);
	for(var i=0; i<vec.length; ++i){
		vec[i] = 0;
	}
	
	var found = false;
	var arr = occ.split('/');
	for(var i=0; i<arr.length; ++i)
	{
		var idx = occupations.indexOf(arr[i].trim());
		if(idx!=-1){
			found = true;
			vec[idx] = 1;
		}
	}
	if (found){
		return vec.join('\t');
	}
	else{
		return "";
	}
}


function filterCountry(c){
	if(c.length==0) return c;
	var countries = ['中国','英国','德国','法国','日本','台湾','香港','澳门','美国',
	                 '韩国','意大利','加拿大','比利时','古巴','印度','罗马尼亚','俄罗斯',
	                 '瑞典','墨西哥','芬兰','西班牙','荷兰','泰国','乌克兰','澳大利亚','伊朗',
	                 '新西兰','波兰','阿根廷','马来西亚','丹麦','爱尔兰date','南非','瑞士','奥地利',
	                 '巴西','匈牙利'];
	
	//过滤国家
	for(var i=0; i<countries.length;++i){
		if(c.indexOf(countries[i])!=-1){
			return countries[i];
		}
	}
	
	var province=['河北','山西','辽宁','吉林','黑龙江','江苏','浙江',
	              '安徽','福建','江西','山东','河南','湖北','湖南',
	              '广东','海南','四川','贵州','云南','陕西','甘肃',
	              '青海','台湾','内蒙古','广西','西藏','宁夏','新疆',
	              '大陆','北京','南京','武汉','上海','乌鲁木齐','西安',
	              '长春','天津','东北','重庆'];
	var taiwan=['台北','台南'];
	
	
	function filterProviince(province,c,name){
		for(var p =0; p<province.length;++p){
			if(c.indexOf(province[p])!=-1){
				return name;
			}
		}
		return c;
	}
	
	
	var name="台湾";
	c = filterProviince(taiwan,c,name);
	if(c==name) return name;
	
	name = '中国';
	c = filterProviince(province,c,name);
	if(c==name) return name;
	
	
	return c;
}


////////////////////////////////////////////////////////////

var db = connect("localhost:27017/douban");
var cur = db.celebrity.find({birth_date:{$exists:true},birth_place:{$exists:true},
	sign:{$exists:true},occupation:{$exists:true},
	name:{$exists:true},gender:{$exists:true}});
	
while(cur.hasNext()){
    var item = cur.next();
    var celebrity_id = item['celebrity_id']
    var name = item['name'];
//    var sign = signs.indexOf(item['sign']);
    var gender = item['gender']=='男'?0:1;
    var place = item['birth_date'];
    var date = item['birth_place'];
    var occupation = item['occupation']
    
    occupation = filterOccupation(occupation);
    if(occupation.length==0){
    	continue;
    }
    
    place = filterCountry(place);
    place =countries.indexOf(place); 
    if(place==-1){
    	continue;
    }
    
    //print(celebrity_id+'\t'+name)
    
    print(celebrity_id+'\t'+
//    	  sign + '\t' +
    	  gender + '\t' +
    	  place + '\t' +
    	  date + '\t' +
    	  occupation 
    	  );
    
}





