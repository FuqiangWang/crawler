



function filterCountry(c){
	if(c.length==0) return c;
	var countries = ['中国','英国','德国','法国','日本','台湾','香港','澳门','美国',
	                 '韩国','意大利','加拿大','比利时','古巴','印度','罗马尼亚','俄罗斯',
	                 '瑞典','墨西哥','芬兰','西班牙','荷兰','泰国','乌克兰','澳大利亚','伊朗',
	                 '新西兰','波兰','阿根廷','马来西亚','丹麦','爱尔兰','南非','瑞士','奥地利',
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


var db = connect("localhost:27017/douban");
var cur = db.celebrity.find();
var genres = new Array();
var mappings = {};
while(cur.hasNext()){
    var item = cur.next();
    if(item.hasOwnProperty('birth_date'))
    	{
    		var sign = item['birth_date'].trim();
    		sign = filterCountry(sign);
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


