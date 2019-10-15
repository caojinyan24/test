package basicExercise.qunar;

//import java.io.BufferedReader;
import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

public class Calculate {
	

	public void Test(){
		//ParseCondition pc=new ParseCondition();
	    //pc.main();
		HotelInfo hi=new HotelInfo();
		hi.main();
	   
	    
	}
	

}
/**
 * �ļ�ͳ�ƣ� 
1) ���ո�����һ�з��飬���������a ->c f(��ȥ��)
2) ͳ��ÿ����ĸ���ֵĴ���
�������ݣ�
b a c
a c f
b f a
c d e
a c c
d e f
 */
class CalAlph{
	
}
/**
 * 3��jvm�ṩ��һ��jstack�Ĺ��ߣ����԰Ѹ�jvm�����е��̶߳�ջ�����������j.stack�ļ� 
����
"DubboServerHandler-192.168.6.96:20880-thread-143" daemon prio=10 tid=0x00007f3d8006d000 
nid=0x1807 waiting on condition [0x00007f3d67cfa000]
java.lang.Thread.State: WAITING (parking)
at sun.misc.Unsafe.park(Native Method)
- parking to wait for <0x00007f3f7c16b630> 
(a java.util.concurrent.SynchronousQueue$TransferStack)
at java.util.concurrent.locks.LockSupport.park(LockSupport.java:158)
at java.util.concurrent.SynchronousQueue$TransferStack.awaitFulfill(SynchronousQueue.java:422)
at java.util.concurrent.SynchronousQueue$TransferStack.transfer(SynchronousQueue.java:323)
at java.util.concurrent.SynchronousQueue.take(SynchronousQueue.java:857)
at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:947)
at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:907)
at java.lang.Thread.run(Thread.java:619)
DubboServerHandler-192.168.6.96:20880-thread-143Ϊ�̵߳����֣�tidΪ�߳�id ,
java.lang.Thread.State: WAITING,WAITINGΪ�߳�״̬
waiting on condition [0x00007f3d67cfa000]��ʾ���߳�waiting��tid=0x00007f3d67cfa000���߳���
��дһ�����򣬽������߳������߳�id���߳�״̬���Լ���Щ�߳�wait��ͬһ��condition�� ��
����˵ƥ�䡰waiting on condition�����߳̽���ͳ�ơ�
���������յȴ�ͬһ��condition���߳����Ӵ�С����
�����ʽ���£�
condition id,count:
�߳�id|�߳���|�߳�״̬
�߳�id|�߳���|�߳�״̬
�߳�id|�߳���|�߳�״̬
condition id,count:
�߳�id|�߳���|�߳�״̬
�߳�id|�߳���|�߳�״̬
����
 */
//list����condition���󣬶������id��count��������count���򣬶�equal���ء�
//condition�������list�����صȴ��̶߳����̶߳�������߳�id�߳�����״̬�ȡ�
//map<String ,Conditon>,���Կ���ͨ��String�ҵ�Condition����Ȼ��count+1������condition����ŵ�list�У���������
class Xiancheng{//
	String id;
	String name;
	String state;
	Xiancheng(String lid,String lname,String lstate){
		id=lid;
		name=lname;
		state=lstate;		
	}
	public String toString(){
		return id+"|"+name+"|"+state+"\n";
	}
	public boolean equals(Xiancheng o){
		return (id.endsWith(o.id)&&name.equals(o.name)&&state.equals(o.state));
	}
}
class Condition implements Comparable<Condition>{
	int count=0;
	String cid=null;
	List<Xiancheng>conlist=new ArrayList<Xiancheng>();
	Condition(String lcid,int lcount){
		cid=lcid;
		count=lcount;
	}
	void addCount(){
		count++;
	}
	void addList(Xiancheng x){
		conlist.add(x);
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("conditon "+cid+","+count);
		sb.append(":\n");
		for(Xiancheng x:conlist){
			sb.append(x);
			//sb.append("\n");
		}
		
		return sb.toString();
	}

	@Override
	public int compareTo(Condition o) {
		// TODO Auto-generated method stub
		return count>o.count?1:(count<o.count?-1:0);
		//return 0;
	}	
}
class ParseCondition{
	Map<String,Condition> map=new HashMap<String,Condition>();
	List<Condition> list=new ArrayList<Condition>();
	void ReadFile(String filename){
		try{
			BufferedReader in=new BufferedReader(new FileReader(filename));
			String s=null;
			try{
				while((s=in.readLine())!=null){
					if(s.contains("waiting on condition")&&s.contains("[")){
						s=s+in.readLine();
						ParseString(s);
					}
					
				}
			}finally{
				in.close();
			}
		}catch (IOException e){
			throw new RuntimeException(e);
		}
		
	}
	void ParseString(String s){//���map��list
		//"Attach Listener" daemon prio=10 tid=0x00007f3d1bff3800 nid=0x324b
		//waiting on condition [0x0000000000000000]
		//  java.lang.Thread.State: RUNNABLE
		int temp=s.indexOf("\"");
		String cid=s.substring(s.indexOf("[")+1,s.indexOf("]"));		
		if((map.get(cid))==null){
			map.put(cid,new Condition(cid,1));
		}
		else{
			map.get(cid).addCount();
		}			
		String xname=s.substring(temp+1,s.indexOf("\"",temp+1));
		String xid=s.substring(s.indexOf("tid=")+(new String("tid=")).length(),s.indexOf("nid="));
		String xstate=s.substring(s.indexOf("State:")+(new String("State:")).length());
		Xiancheng xianct=new Xiancheng(xid,xname,xstate);
		map.get(cid).addList(xianct);
		list.add(map.get(cid));
	}
	void main(){
		ReadFile("j.stack");
		Collections.sort(list);
		Collections.reverse(list);
		//return list.toString();
		for(Condition c:list){
			System.out.print(c);
		}
	}
}
/**
 * ���룺����һ��hotelinfo�ļ����ļ���ʽ���£� 
shanghai_city_7208 �Ϻ�ȫ���Ƶ껴��·��
shanghai_city_14744 ����֮���Ϻ���ɽ����ɳ̲��
jinan_2794 �����д����ù�
carmel_ca_5 Carmel River Inn
��ʽ˵����
1)һ�����У�֮��ʹ��tab�ָ�
2)��һ���ǾƵ���ţ��ڶ����ǾƵ�����
3)��shanghai_city_7208Ϊ����ǰ���shanghai_city�������
Ҫ�����£�
1. ���һ���ļ�����hotelinfo��ʽһ�������ǰ��վƵ���Ž��н�������//map���棬�޸�tostring���������ʽ��
2. ���һ���ļ������У���һ���ǳ��д��ţ��ڶ�������������µľƵ��������ǰ��վƵ������н�������//�����࣬ÿ��������һ���Ƶ���Ϣ���б����ݾƵ���Ŀ����
*///��һ��map��ͨ��string���ҵ�city����city�����а���count��hotel�б�//ͨ��map���Եõ�һ������alue��set
//public Collection<V> values()
class City implements Comparable<City>{
	//List<Hotel> hlist=new ArrayList<Hotel>();
	int count=0;
	String citycode=null;
	City(String cc,int c){
		citycode=cc;
		count=c;
	}
	public String toString(){
		return citycode+"\t"+count+"\r\n";
	}
	void addCount(){
		count++;
	}
	
	//void addHotel()
	@Override
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		//return 0;
		return count>o.count?1:(count<o.count?-1:0);
	}	
}
class HotelInfo {	
	SortedMap<String,String>smap=new TreeMap<String,String>();//����Ƶ���ź�����	
	//Set<City>set=new HashSet<City>();
	Map<String,City>cmap=new HashMap<String,City>();
	void ReadFile(String filename){//��ȡ�����smap,list
		try{
			BufferedReader br=new BufferedReader(new FileReader(filename));
			String s;
			try{
				while((s=br.readLine())!=null){
					String stemp[]=s.split("\t");
					if(stemp.length>=2){
						smap.put(stemp[0], stemp[1]);
						String scity=stemp[0].substring(0,stemp[0].lastIndexOf("_")) ;
						if(cmap.get(scity)==null){
							cmap.put(scity,new City(scity,1));
						}
						else
							cmap.get(scity).addCount();
					}
					
					
				}
			}finally{
					br.close();
				}
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}
	void WriteHotel(String outfile){
		try{			
			PrintWriter pw=new PrintWriter(outfile);
			try{
				//StringBuilder sb=new StringBuilder();
				Set<Map.Entry<String,String>> i=(Set<Map.Entry<String,String>>)smap.entrySet();
				//Entry<String,String> i=((TreeMap<String, String>) smap).lastEntry();				
				Iterator<Entry<String, String>> it= i.iterator();
			    do{
				   Entry<String,String> e = it.next();				   
				   pw.println(e.getKey()+"\t"+e.getValue());	 				
			      }while(it.hasNext());
			 }finally{
				pw.close();
			 }
			
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	void WriteCity(String outfile){
		try{
			PrintWriter pw=new PrintWriter(outfile);
			try{				
				//Object[] clist=cmap.values().toArray();
				
				Collection<City> c=cmap.values();
				List<City>list=new ArrayList<City>(c);
				Collections.sort(list);
				Collections.reverse(list);
				//Arrays.sort(clist);
				for(City cl:list)
				   pw.print(cl);
				//System.out.print(list);
			}finally{
				pw.close();
			}
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	void main(){
		ReadFile("hotelinfo.txt");
		WriteHotel("hotelinfoout1.txt");
		WriteCity("hotelinfoout2.txt");
	}
}
/**
 * ���¸�ʽ 2013-10-01��2013-10-02 100��ʾ2013-10-01��ס��2013-10-02��꣬��סһ��ļ۸��ǣ�100�� 
�����ļ��а����������������ڼ۸�Σ��뽫��ϲ����ϲ��Ĺ��������
1)�۸���ͬ�����ڶ����ڻ����ص�����Ҫ�ϲ�
2)��ͬ���ڵļ۸��Ѻ���¼���Ϊ׼
����1��
2013-08-01��2013-08-31 300
2013-08-25��2013-09-30 300
�ϲ������
2013-08-01��2013-09-30 300
����2��
2013-08-01��2013-12-31 300
2013-10-01��2013-10-07 500
�ϲ�֮�����
2013-08-01��2013-09-30 300
2013-10-01��2013-10-07 500
2013-10-08��2013-12-31 300
�����ָ���ļ�price.txt��data.txt���������е����ڼ۸�κϲ��󣬰�����ס��������չʾ�����������̨���ɡ�
*/



/**
����������һ����HotelCenter�����з���List<Hotel> getHotelList(String city)���Ի��һ�����������оƵ��б�
Hotel������������String name, String address, int price, Map<String, String> properties
�Ƶ�ķ�name��address�����Զ�����ͨ��properties.get(key)�õ�
������Ҫʵ�����¹���
����ҳ�洫��Ĳ������ض�Ӧ�ľƵ��б�������ֵ�Ի���&�ָ������ֵ����ж�������ж��ŷָ�
�����κβ���������ȱʧ����Ӧ��ֵ���Ϊ�մ���Ҳ�����ò��������ڴ���
���������������ɣ����˲�������������ͷ�ҳ����
���˲�������
city���Ƶ����ڳ���)
name���Ƶ�������name��ֵ)
address���Ƶ��ַ����address��ֵ)
brand���Ƶ�Ʒ������brand��ֵ��һ��)
star���Ƶ��Ǽ�����star��ֵ�е�һ��)
price���Ƶ�۸���priceֵ���䷶Χ��)
area���Ƶ������������areaֵ�е�һ��)
�����������
sort������sort��ֵ�����������ֵ��price���Ͱ��ռ۸�����������ֵ��star�������Ǽ���������)
order��ֵ�����asc����������desc���ǽ���)
�������ȱʧʱ��Ĭ�ϰ���sort=price&order=asc����
��ҳ��������
page��page��ֵ����Ҫ���ľƵ���ʵ����ֵ����ֹ����ֵ��������ҿ������ѡ�������û�����ݣ��򲻴�������һ����30���Ƶ꣬page=20-40����Ҫ���غ�10���Ƶ�)
��ҳ����ȱʧʱ��Ĭ�ϰ���0-20����
������һ���������������
city=����&name=�Ƶ�&address=�����ׯ&brand=7��,���&star=3,4&price=100-200,300-500&area=�йش�&sort=price&order=desc&page=100-120
*/
class Hotel{
	String name;
	String address;
	int price;//�Ƶ�ķ�name��address�����Զ�����ͨ��properties.get(key)�õ�
	Map<String,String> properties;
	
}
class HotelCenter{
	List<Hotel> getHotelList(String city){return new ArrayList<Hotel>();	}
	List<Hotel> outputlist=new ArrayList<Hotel>();
	String[] comm={};
	void readToList(String command){//����������в��ҷ���������hotel����outputlist
		//city=����&name=�Ƶ�&address=�����ׯ&brand=7��,���&star=3,4&price=100-200,300-500&area=�йش�
		String[]comm=command.split("&");
		//Map<String,String>map=new HashMap<String,String>();
		for(String s:comm){
			if(s.contains("=")){
				String[] temp=s.split("=");
				parseComm(temp[0],temp[1]);
			}
		}
	}
	void parseComm(String comm1,String comm2){
		if(comm1=="city"){
			if(!comm2.contains(",")){
				outputlist=getHotelList(comm2);
			}
			else{
				String city[]=comm2.split(",");
				for(String s:city){
					outputlist.addAll(getHotelList(s));
				}
				
			}
			
		}//��getHotelList�л��ɸѡ���е����еľƵ겢��ӵ�outputlist��
		else if(comm1=="name"){
			if(!comm2.contains(",")){
				//outputlist.rgetHotelList(comm2);
				for(Hotel h:outputlist){
					if(h.name!=comm2){
						outputlist.remove(h);
					}
				}
			}
			else{
				String name[]=comm2.split(",");
				for(Hotel h:outputlist){	
					boolean ischoosen=false;
					for(String s:name){
						if(h.name==s){
							ischoosen=true;
						}						
					}
					if(!ischoosen){
						outputlist.remove(h);
					}
				}				
			}
		}//name
		else if(comm1=="address"){
			if(!comm2.contains(",")){
				//outputlist.rgetHotelList(comm2);
				for(Hotel h:outputlist){
					if(h.address!=comm2){
						outputlist.remove(h);
					}
				}
			}
			else{
				String address[]=comm2.split(",");
				for(Hotel h:outputlist){	
					boolean ischoosen=false;
					for(String s:address){
						if(h.address==s){
							ischoosen=true;
						}						
					}
					if(!ischoosen){
						outputlist.remove(h);
					}
				}				
			}			
		}//address
		else if(comm1=="brand"){
			if(!comm2.contains(",")){
				//outputlist.rgetHotelList(comm2);
				for(Hotel h:outputlist){
					if(h.properties.get("brand")!=comm2){
						outputlist.remove(h);
					}
				}
			}
			else{
				String brand[]=comm2.split(",");
				for(Hotel h:outputlist){	
					boolean ischoosen=false;
					for(String s:brand){
						if(h.properties.get("brand")==s){
							ischoosen=true;
						}						
					}
					if(!ischoosen){
						outputlist.remove(h);
					}
				}				
			}					
		}//brand
		else if(comm1=="star"){
			if(!comm2.contains(",")){
				//outputlist.rgetHotelList(comm2);
				for(Hotel h:outputlist){
					if(h.properties.get("star")!=comm2){
						outputlist.remove(h);
					}
				}
			}
			else{
				String star[]=comm2.split(",");
				for(Hotel h:outputlist){	
					boolean ischoosen=false;
					for(String s:star){
						if(h.properties.get("star")==s){
							ischoosen=true;
						}						
					}
					if(!ischoosen){
						outputlist.remove(h);
					}
				}				
			}		
		}//star
		else if(comm1=="price"){
			if(!comm2.contains(",")){
				//outputlist.rgetHotelList(comm2);
				for(Hotel h:outputlist){
					if(h.properties.get("price")!=comm2){
						outputlist.remove(h);
					}
				}
			}
			else{
				String price[]=comm2.split(",");
				for(Hotel h:outputlist){	
					boolean ischoosen=false;
					for(String s:price){
						if(h.properties.get("price")==s){
							ischoosen=true;
						}						
					}
					if(!ischoosen){
						outputlist.remove(h);
					}
				}				
			}		
		}//price
		else if(comm1=="area"){
			if(!comm2.contains(",")){
				//outputlist.rgetHotelList(comm2);
				for(Hotel h:outputlist){
					if(h.properties.get("area")!=comm2){
						outputlist.remove(h);
					}
				}
			}
			else{
				String area[]=comm2.split(",");
				for(Hotel h:outputlist){	
					boolean ischoosen=false;
					for(String s:area){
						if(h.properties.get("area")==s){
							ischoosen=true;
						}						
					}
					if(!ischoosen){
						outputlist.remove(h);
					}
				}				
			}		
		}
	}//area
	void sortList(String command){
		//sort=price&order=desc&page=100-120
		String sorttemp=null;
		String ordertemp=null;
		if(command.contains("sort")){
			int temp=command.indexOf("sort");
			sorttemp=command.substring(temp+5,command.indexOf("&",temp));			
		}
		if(command.contains("order")){
			int temp=command.indexOf("order");
			ordertemp=command.substring(temp+6,command.indexOf("&",temp));			
		}
		if(sorttemp=="star"&&ordertemp=="desc")
			Collections.sort(outputlist,new ComparatorstarL<Hotel>());
		else if(sorttemp=="star")
			Collections.sort(outputlist,new ComparatorstarH<Hotel>());
		else if(ordertemp=="desc")
			Collections.sort(outputlist,new ComparatorpriceL<Hotel>());
		else
			Collections.sort(outputlist,new ComparatorpriceH<Hotel>());
	}
	void printList(String command){//page=100-120
		int listlength=outputlist.size();
		int tempi[]=new int[2];
		if(listlength<20){
			tempi[0]=0;
			tempi[1]=listlength;
		}
		else{
			tempi[0]=0;
			tempi[1]=20;
		}			
		//{0,20};
		if(command.contains("page")){
			String temp=command.substring(command.indexOf("page=")+5);
			String temps[]=temp.split("-");
			int ti[]={Integer.valueOf(temps[0]),Integer.valueOf(temps[1])};	
			if(ti[0]>=listlength)
				;
			else if(ti[0]<listlength&&ti[1]>=listlength){
				tempi[0]=ti[0];
				tempi[1]=listlength;				
			}
			else if(ti[1]<listlength){
				tempi[0]=ti[0];
				tempi[1]=ti[1];
			}
		}
		System.out.println(outputlist.subList(tempi[0], tempi[1]));
	}
}


class ComparatorpriceL<T> implements Comparator<Hotel>{

	@Override
	public int compare(Hotel o1, Hotel o2) {
		// TODO Auto-generated method stub
		//return 0;
		return o1.price>o2.price?1:(o1.price<o2.price?-1:0);
	}
	
}
class ComparatorpriceH<T> implements Comparator<Hotel>{

	@Override
	public int compare(Hotel o1, Hotel o2) {
		// TODO Auto-generated method stub
		//return 0;
		return o1.price>o2.price?-1:(o1.price<o2.price?1:0);
	}
	
}

class ComparatorstarH<T> implements Comparator<Hotel>{

	@Override
	public int compare(Hotel o1, Hotel o2) {
		// TODO Auto-generated method stub
		return 0;
		//return compare((String)o1.properties.get("star"),(String)o2.properties.get("star"));
	}
	
}

class ComparatorstarL<T> implements Comparator<Hotel>{

	@Override
	public int compare(Hotel o1, Hotel o2) {
		// TODO Auto-generated method stub
		return 0;
		//return o1.properties.get("star")>o2.properties.get("star")?-1:(o1.properties.get("star")<o2.properties.get("star")?1:0);
	}
	
}