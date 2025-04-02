///**
//* ����2���ļ�
//Ҫ��
//����properties�������滻��txt��$function(index)��ʽ���֣����仹ԭ��һ������С˵��
//����function��4�ֺ������滻�������£�
//1)natureOrder ��Ȼ���򣬼��ı�������˳��
//2)indexOrder ���������ı���ÿ�е�һ������Ϊ����
//3)charOrder �ı�����java���ַ�����
//4)charOrderDESC �ı�����java���ַ�����
//ע�⣺txt��properties�ļ���Ҫ�������ֶ��ֽ��������������سɱ����ļ���
//5)ͨ��ִ��com.qunar.fresh.task.checks.Check3 �鿴�Լ�����������ʽ���£�name true time��
//�ڶ���true��ʾ�����ȷ��false�������ȷ��������time��ִ��ʱ�䣬����Ϊ��λ��
//*/
//package replace;
//
//public class Replace {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
/**
* ����2���ļ�
Ҫ�󣺶��������ļ�
����properties�������滻��txt��$function(index)��ʽ���֣����仹ԭ��һ������С˵��
����function��4�ֺ������滻�������£�
1)natureOrder ��Ȼ���򣬼��ı�������˳��
2)indexOrder ���������ı���ÿ�е�һ������Ϊ����
3)charOrder �ı�����java���ַ�����
4)charOrderDESC �ı�����java���ַ�����
�����һ���ļ���
*/
/**
* �����ַ������滻�ض����ַ�
* $func(1)���ɡ�1��
*/
package basicExercise.qunar;
//import java.lang.Character;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
public class Replace {
	public void Test(){
		//String s1=new String("$func1(123)hsd$func2(5)abc$");
	    //ReplaceString para=new ReplaceString();
	    //System.out.print(para.ReplaceString2(s1));
	    //RepaceFile rf=new RepaceFile();
	    //rf.main();
	    //Shendiao sd=new Shendiao();
	    //sd.main();
		Calender c=new Calender();
		c.main();
	}
	
	
	

}
class ReplaceString{
	String newString=new String("");
	StringBuilder sb=new StringBuilder();
	/**
		 * �����ַ������滻�ض����ַ�
		 * $func(1)���ɡ�1��
		 */
	public String ReplaceString1(String str,String olds,String news){
		
		
		String toBeReplace=new String("$func(");
		int beginIndex=0;
		int middleIndex=str.indexOf(toBeReplace,beginIndex);
		//int endIndex=str.indexOf(')',middleIndex);
		
	
			while(middleIndex>=beginIndex){//�ҵ�һ��ǰƥ���ַ���
				int endIndex=str.indexOf(')',middleIndex);
				if(endIndex!=-1&&str.substring(middleIndex+toBeReplace.length(),endIndex).matches("[+,-]*[0-9]*")){
					//�ҵ�һ�����滻��
					String integerChar=str.substring(middleIndex+toBeReplace.length(),endIndex);
					
					   sb.append(str.substring(beginIndex,middleIndex));
					   sb.append(integerChar);
					   beginIndex=middleIndex+toBeReplace.length()+integerChar.length()+1;
					   middleIndex=str.indexOf(toBeReplace, beginIndex);
				}
				else{//ֻƥ����ǰ���֣��󲿷ֲ�ƥ�䣬���¿�ʼ
					
					sb.append(str.substring(beginIndex,middleIndex+toBeReplace.length()));
					beginIndex=middleIndex+toBeReplace.length();
					middleIndex=str.indexOf(toBeReplace,middleIndex+toBeReplace.length());
					
			    }				
			}
			 sb.append(str.substring(beginIndex));//���ַ������ʣ�Ĳ������ȥ
			 return sb.toString();
		
	}
	/**
	 * �����ַ������滻�ض����ַ�
	 * $func1(1)���������������
	 * $func2(1)�������ֵ�����
	 * //���Զ�str�������Σ���һ���滻�����е�toBeReplace1���ڶ����滻�����е�toBeReplace2�����ַ�ʽ��Ҫ�����ı����Σ�
	 *  //����$Ȼ��ȽϺ�ߵ��ַ���
	 */
	public String ReplaceString2(String str){		
		List<String> list=new ArrayList<String>();
		list.add("func1(");
		list.add("func2(");
		int beginIndex=0;
		int middleIndex=str.indexOf('$',beginIndex);
		while(middleIndex>=beginIndex&&beginIndex<str.length()){//�ҵ�һ��ƥ���$
			boolean isfrontMatch=false;
			for(int i=0;i<list.size();++i){
				if(str.startsWith(list.get(i), middleIndex+1)){//�ҵ�һ��ǰƥ��
					//TODO:����֮���middleIndex��beginIndex���¸�ֵ
					//
					isfrontMatch=true;
					int endIndex=str.indexOf(')',middleIndex+list.get(i).length()+1);
					if(endIndex!=-1&&str.substring(middleIndex+list.get(i).length()+1,endIndex).matches("[+,-]*[0-9]*")){
						//�ҵ�һ�����滻��
						String integerChar=str.substring(middleIndex+list.get(i).length()+1,endIndex);
						int temp=Integer.valueOf(integerChar);
						   sb.append(str.substring(beginIndex,middleIndex));
						   if(i==0)
							   sb.append(integerChar);						   
						   else if(i==1)
							   sb.append(Integer.toString(temp*2));
						   beginIndex=endIndex+1;
						   middleIndex=str.indexOf('$', beginIndex);
						   break;
						  
					}
					else{//ֻƥ����ǰ���֣��󲿷ֲ�ƥ�䣬���¿�ʼ
						
						sb.append(str.substring(beginIndex,middleIndex+list.get(i).length()+1));
						beginIndex=middleIndex+list.get(i).length()+1;
						middleIndex=str.indexOf('$',beginIndex);
						
						break;
				    }				
					
					//break;				
				}					
			}
			//TODO:ǰƥ�䲻�ɹ�����beginIndex��middleIndex���¸�ֵ֮�󣬻ص�whileѭ��		
			if(!isfrontMatch){
				sb.append(str.substring(beginIndex,middleIndex+1));
			    beginIndex=middleIndex+1;
			    middleIndex=str.indexOf('$',beginIndex);
			}
					
			
		}
		//TODO:�Ѻ��ʣ����ַ������ȥ
		sb.append(str.substring(beginIndex));		
		return sb.toString();		
	}
}
/**
 * �������ļ�context.txt��words.conf,�볢�Խ����Ǻϲ���Ϊһ�����֣�����ӡ������

�������ļ��������£�               
context.txt
��������ÿ���˶���Ҫ$(qunar)�Լ�����ʳ��$(flight.1)ÿ���˶���Ҫ���Լ�����$(flight.2)������˵�ű��˷�����$(hotel)��ʹ�ñ��˷�������ѧ......����һֱ��$(tuan)���˵ĳɹ���ʹ����������о����֪ʶ$(travel.1)�����У���һ��$(travel.2)�����顱 
word.conf
flight=Ҳ���ǣ��·�
qunar=��ֲ
hotel=����
tuan=ʹ��
travel=�������죺���˲���
*/
class RepaceFile{
	Map<String,String> map=new HashMap<String,String>();
	String txt="";
	StringBuilder sb=new StringBuilder();
	//
	void main(){
		ReadTxt("context.txt");
		ReWrite("word.conf");
		WriteToFile("out1.txt");
	}
	void ReWrite(String file){
		
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			try{
				String s;
			    while((s=br.readLine())!=null){
			    	String[] smap=s.split("=");
			    	if(smap.length>=2&&smap[1].contains("��")){
			    		String[] smaps=smap[1].split("��");
			    		if(txt.contains("$("+smap[0]+".1)")){
			    			txt=txt.replace("$("+smap[0]+".1)", smaps[0]);
			    		}
			    		if(txt.contains("$("+smap[0]+".2)")){
			    			txt=txt.replace("$("+smap[0]+".2)", smaps[1]);
			    		}
			    				
			    	}
			    	else if(smap.length>=2){
			    		if(txt.contains("$("+smap[0]+")")){
			    			txt=txt.replace("$("+smap[0]+")", smap[1]);
			    		}
			    	}
			
		        }
			}finally{
				br.close();
			}
		    
		}catch (IOException e){
			throw new RuntimeException(e);
		}
		
	}
	void ReadTxt(String file){
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			try{
				String s;
			    while((s=br.readLine())!=null){
			    	sb.append(s);
		        }
			    txt=sb.toString();
			}finally{
				br.close();
			}
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}
	void WriteToFile(String outfile){
		
		try{
			PrintWriter out=new PrintWriter(outfile);
			try{
				out.print(txt);
			}finally{
				out.close();
			}		
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		
	}
}
/**
3������ļ��滻��
����2���ļ�����ַ�ڣ�
http://fresh.qunar.com/sites/task3.properties
http://fresh.qunar.com/sites/task3.txt


Ҫ��
����properties�������滻��txt��$function(index)��ʽ���֣����仹ԭ��һ������С˵��
����function��4�ֺ������滻�������£�
1)natureOrder ��Ȼ���򣬼��ı�������˳��//getline˳��//linkedlist
2)indexOrder ���������ı���ÿ�е�һ������Ϊ����//������Ϊkey��map
3)charOrder �ı�����java���ַ�����//���պ��ߵ�������//sortedlist
4)charOrderDESC �ı�����java���ַ�����//���պ�ߵ��ֵĵ���
ע�⣺txt��properties�ļ���Ҫ�������ֶ��ֽ��������������سɱ����ļ���
5)ͨ��ִ��com.qunar.fresh.task.checks.Check3 �鿴�Լ�����������ʽ���£�name true time��
�ڶ���true��ʾ�����ȷ��false�������ȷ��������time��ִ��ʱ�䣬����Ϊ��λ��
*/
class Shendiao{
	//��һ��String������nature��index�ȣ���һ��map�������ֺͶ�Ӧ���滻���֣��ܲ���ͨ������׼˳�򣬱��浽���ض�˳��洢���б��У�
	//Map<String,Map<String,String>>
	//SortedMap<K,V>
	Map<String,String> map=new HashMap<String,String>();//indexorder
	//natureSortedSet nss=new LinnatureSortedSet();
	List<String> list=new ArrayList<String>();//natureorder
	//Set<String>hs=new HashSet<String>();
	//Object[] hslist={};
    //Object[] hsDlist={};
	//TreeSet coss=new TreeSet();
	//charOrderDESCSortedSet codss=new charOrderDESCSortedSet();
	Object[] hslist;
	Object[] hsDlist;	
	StringBuilder sb=new StringBuilder();
	void ReadToBuffer(String strUrl){//���map,set
		try{
			//String strUrl="http://fresh.qunar.com/sites/task3_prop.txt";
			URL url = new URL(strUrl);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.connect();
			BufferedInputStream bis = new BufferedInputStream(urlconnection.getInputStream());			
			BufferedReader br=new BufferedReader(new InputStreamReader(bis,"UTF-8"));      
			//BufferedReader br=new BufferedReader(new FileReader(file));
			String s;
			try{
				while((s=br.readLine())!=null){
			   
				String[] st=s.split("\t");
				if(st.length>=2){
					map.put(st[0], st[1]);
				//hs.add(st[1]);
				list.add(st[1]);
				}				
				}
				 hslist=list.toArray();
				 hsDlist=list.toArray();
				Arrays.sort(hslist);
				Arrays.sort(hsDlist,Collections.reverseOrder());
				
			}finally{
				br.close();
			}
			
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		
		
		
	}
	void ReplaceTxt(String strUrl,String outfile){//���ж���txt���ݲ��滻����ӵ�sb
		
		try{
			//String strUrl=" http://fresh.qunar.com/sites/task3.txt";
			URL url = new URL(strUrl);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.connect();
			BufferedInputStream bis = new BufferedInputStream(urlconnection.getInputStream());			
			BufferedReader br=new BufferedReader(new InputStreamReader(bis,"UTF-8"));      
			//BufferedReader br=new BufferedReader(new FileReader(file));
			PrintWriter pw=new PrintWriter(outfile);
			String stemp;
			try{
				while((stemp=br.readLine())!=null){//TODO:�������ڱ��ʽ���ֻ��е����
					stemp=ReplaceStr1(stemp,"$natureOrder(",list.toArray());//���� ���е�$function(index)���滻����ӵ�sb
					stemp=ReplaceStr1(stemp,"$charOrder(", hslist);//TODO:��stemp���ı䲻�ᴫ����//������ΪString�ǻ������ͣ���
					stemp=ReplaceStr1(stemp,"$charOrderDESC(",hsDlist);
					stemp=ReplaceStr1(stemp,"$indexOrder(",map);
					pw.println(stemp);//sb.append(stemp);
					
				}				
				
			   }finally{
				br.close();
				pw.close();
			   }
			}catch (IOException e){
			throw new RuntimeException(e);
		    }			
		
	}
	void main(){
		ReadToBuffer("http://fresh.qunar.com/sites/task3_prop.txt");
		ReplaceTxt("http://fresh.qunar.com/sites/task3.txt","outurl.txt");
		//WriteOut("out2.txt");
		
	}
	void WriteOut(String outfile){
		try{
			PrintWriter pw=new PrintWriter(outfile);
			
			try{
				pw.print(sb.toString());							
				
			   }finally{
				pw.close();
			   }
			}catch (IOException e){
			throw new RuntimeException(e);
		    }	
	}
    String ReplaceStr(String str,String olds,Object[] list){
		
		
		String toBeReplace=olds;//new String("$func(");
		int beginIndex=0;
		int middleIndex=str.indexOf(toBeReplace,beginIndex);
		//int endIndex=str.indexOf(')',middleIndex);
		
	
			while(middleIndex>=beginIndex){//�ҵ�һ��ǰƥ���ַ���
				int endIndex=str.indexOf(')',middleIndex);
				if(endIndex!=-1&&str.substring(middleIndex+toBeReplace.length(),endIndex).matches("[+,-]*[0-9]*")){
					//�ҵ�һ�����滻��
					String integerChar=str.substring(middleIndex+toBeReplace.length(),endIndex);
					//int middleInt=Integer.parseInt(integerChar)
					  // sb.append(str.substring(beginIndex,middleIndex));
					  // sb.append(integerChar);
					   beginIndex=middleIndex+toBeReplace.length()+integerChar.length()+1;
					   middleIndex=str.indexOf(toBeReplace, beginIndex);
					   str=str.replace(olds+integerChar+")", (String)list[Integer.parseInt(integerChar)]);
				}
				else{//ֻƥ����ǰ���֣��󲿷ֲ�ƥ�䣬���¿�ʼ
					
					sb.append(str.substring(beginIndex,middleIndex+toBeReplace.length()));
					beginIndex=middleIndex+toBeReplace.length();
					middleIndex=str.indexOf(toBeReplace,middleIndex+toBeReplace.length());
					
			    }				
			}
			//pw.println(stemp);
			 //sb.append(str.substring(beginIndex));//���ַ������ʣ�Ĳ������ȥ
			 return str;
		
	}
String ReplaceStr1(String str,String olds,Object[] list){		
		
		if(str.contains(olds)&&str.contains(")")&&str.substring(str.indexOf('(')+1,str.indexOf(')')).matches("[+,-]*[0-9]*")){		
			int middlenum=Integer.parseInt(str.substring(str.indexOf('(')+1, str.indexOf(')')));
			str=str.replace(olds+str.substring(str.indexOf('(')+1,str.indexOf(')'))+')', (String)list[middlenum]);
		}
		return str;		
	}
      String ReplaceStr(String str,String olds,Map<String,String> map){
		
		
		String toBeReplace=olds;//new String("$func(");
		int beginIndex=0;
		int middleIndex=str.indexOf(toBeReplace,beginIndex);
		//int endIndex=str.indexOf(')',middleIndex);
		
	
			while(middleIndex>=beginIndex){//�ҵ�һ��ǰƥ���ַ���
				int endIndex=str.indexOf(')',middleIndex);
				if(endIndex!=-1&&str.substring(middleIndex+toBeReplace.length(),endIndex).matches("[+,-]*[0-9]*")){
					//�ҵ�һ�����滻��
					String integerChar=str.substring(middleIndex+toBeReplace.length(),endIndex);
					//int middleInt=Integer.parseInt(integerChar)
					  // sb.append(str.substring(beginIndex,middleIndex));
					  // sb.append(integerChar);
					   beginIndex=middleIndex+toBeReplace.length()+integerChar.length()+1;
					   middleIndex=str.indexOf(toBeReplace, beginIndex);
					   str=str.replace(olds+integerChar+")", map.get(integerChar));
				}
				else{//ֻƥ����ǰ���֣��󲿷ֲ�ƥ�䣬���¿�ʼ
					
					//sb.append(str.substring(beginIndex,middleIndex+toBeReplace.length()));
					beginIndex=middleIndex+toBeReplace.length();
					middleIndex=str.indexOf(toBeReplace,middleIndex+toBeReplace.length());
					
			    }				
			}
			 //sb.append(str.substring(beginIndex));//���ַ������ʣ�Ĳ������ȥ
			 return str;
		
	}
      String ReplaceStr1(String str,String olds,Map<String,String> map){

  		if(str.contains(olds)&&str.contains(")")&&str.substring(str.indexOf('(')+1,str.indexOf(')')).matches("[+,-]*[0-9]*")){		
  			int middlenum=Integer.parseInt(str.substring(str.indexOf('(')+1, str.indexOf(')')));
  			str=str.replace(olds+str.substring(str.indexOf('(')+1,str.indexOf(')'))+')', map.get(new Integer(middlenum).toString()));
  		}
  		return str;		
  		
  	}
	
}


/**
������¹��ܣ�
1)��һ��(����)����������N��properties�Լ�getter��setter����
2)��һ��properties�ļ�����N��key,value������������property��ֵ
3)��һ��scheme�̶���xml�����������������
Ҫ��дһ����������
1)��xml�е�ռλ�����滻Ϊproperties�ļ��е�value
2) ��xml�����ɶ��󣬵���getter������ʱ����Ի��ֵ
3)����������˼�룬ʹ�ý���������չ��
���Ӽ�������ע�⣺
1)������������󣬲��������е�Student�������е�property����java�е�ԭ������
2)xml��properties��ʹ�õ�ʱ���Ǹ��ݶ������úõ�
3) xml��scheme�ǹ̶��ģ����Ǹ����е�scheme
name=a
age=10
birth=2003-11-04
<object class="com.qunar.fresh.Student">
	<property name="name">
		<value>${name}</value>
	</property>
	<property name="age">
		<value>${age}</value>
	</property>
	<property name="birth">
		<value>${birth}</value>
	</property>
</object>
*/
//class tt{
//	void main(){
//		try{
//		PrintWriter pw=new PrintWriter("out.txt");
//		int year;
//
//		int month;
//		Scanner input=new Scanner(System.in);
//
//		boolean xn = false;
//
//		do
//
//		{
//
//		System.out.println("���������:");
//
//		year = input.nextInt();
//
//		System.out.println("�������·�:");
//
//		month = input.nextInt();
//
//		//����boolean���͵ı��ʽ����������Ϣ���ж�
//
//		xn = (month < 1) || (month > 12) ||(year < 1);
//
//		if(xn)
//
//		{
//
//		System.out.println("������Ϣ�������������룡");
//
//		}
//
//		}while(xn);
//
//	
//		int everyYearDay = 0; //ÿ�������
//
//		int totalYearsDays = 0; //�����������
//
//		int inputYearDay = 0 ;   //��¼�û������������
//
//		boolean yn = false;   //��ʶƽ����
//
//
//
//		//����forѭ����������
//
//		for(int i = 1900;i <=year;i ++)
//
//		{
//
//		if(((i % 4 == 0)&&(i % 100 != 0))||(i % 400 == 0))  //������ж�����
//
//		{
//
//		yn = true;
//
//		everyYearDay = 366;
//
//		}
//
//		else
//
//		{
//
//		yn = false;
//
//		everyYearDay = 365;
//
//		}
//
//		//���ѭ���е����С���û���������,���ۻ�����
//
//		if(i < year)
//
//		{
//
//		totalYearsDays = totalYearsDays + everyYearDay;
//
//		}
//
//		else
//
//		{
//
//		inputYearDay = everyYearDay;
//
//		//System.out.println(year + "�깲��:" + inputYearDay + "��");
//		pw.println(year + "�깲��:" + inputYearDay + "��");
//
//		}
//
//		}
//
//		//�����ж��·ݵ������������㵱��1����(�û������·� -1)������
//
//		//Code:
//		int everyMonthDay = 0;    //��¼ÿ�µ�����
//
//		int totalMonthsDays = 0; //������
//
//		int inputMonthDay = 0;    //��¼�û�������·����û�������ݵ�����
//
//
//
//		//����forѭ����������
//
//		for(int i = 1;i <= month;i ++)
//
//		{
//
//		switch(i)
//
//		{
//
//		case 4:
//
//		case 6:
//
//		case 9:
//
//		case 11:
//
//		everyMonthDay = 30;
//
//		break;
//
//		case 2:
//
//		if(xn)   //xn��������¼ƽ�����boolean���͵ı���
//
//		{
//
//		everyMonthDay = 29;
//
//		}
//
//		else
//
//		{
//
//		everyMonthDay = 28;
//
//		}
//
//		break;
//
//		default:
//
//		everyMonthDay = 31;
//
//		break;
//
//		}
//
//		if(i < month)
//
//		{
//
//		totalMonthsDays = totalMonthsDays + everyMonthDay;
//
//		}
//
//		else
//
//		{
//
//		inputMonthDay = everyMonthDay;
//
//		//System.out.println(month + "�¹���:" + inputMonthDay + "��");
//		pw.println(month + "�¹���:" + inputMonthDay + "��");
//
//		}
//
//		}
//
//		//�ġ���������������������û�������·ݵĵ�һ�����ڼ�
//
//		//Code:
//		int total = totalMonthsDays + totalYearsDays; //����������
//
//
//
//		int temp = (total + 1) % 7; //�ж������·ݵĵ�һ�����ڼ�
//
//		//�塢��ʽ�����
//
//		//Code:
//		//��Ϊ���ǵ������ʽ��
//
//		//������ ����һ ���ڶ� ������ ������ ������ ������
//
//		//��������ʱ��ʱ������ֱ������ͺ��ˣ����ǵ�
//
//		//��һ��������һ��ʱ�����Ǿͱ������ȴ�ӡ���ո�
//
//		//Ȼ����������ڣ������ú�����������Ӧ
//
//		//��ӡ�ո�
//
//		for(int spaceno = 0;spaceno < temp;spaceno ++)
//
//		{
//
//		//System.out.print("\t");
//		pw.print("\t");
//
//		}
//
//
//
//		//����˳���ӡ����
//
//		for(int i = 1;i <= inputMonthDay;i ++)
//
//		{
//
//		if((total + i ) % 7==0)     //�ж��Ƿ�û�����
//
//		{
//
//		//System.out.println(i );
//		pw.println(i );
//
//		}
//
//		else
//
//		{
//
//		//System.out.print(i + "/t");
//		pw.print(i + "\t");
//
//		}
//
//		}  pw.close();
//		}
//		
//		catch(IOException e){
//			throw new RuntimeException(e);
//		}
//
//	}
//}
class Calender{		
	int OffSet=0;//����ÿ��ĵ�һ�������ڼ�
	List<Integer> month=new LinkedList<Integer>();//�洢һ����ÿһ�������	
	
	void initData(){
		//TODO�������������
		Scanner input=new Scanner(System.in);
		int YearInit=1900;
		int YearIn=1900;
		try{
			System.out.println("input year:");
			YearIn=input.nextInt();
			while(YearIn<YearInit){
				System.out.println("after 1900,input again!");
				YearIn=input.nextInt();
			}		
		}finally{
			input.close();
		}
		//TODO��month��ֵ
		int MON=12;
		int DMon=31;
		int XMon=30;
		int DFeb=28;		
		if((YearIn%400==0)||(YearIn%100!=0&&YearIn%4==0)){			
			DFeb=29;
		}//�ж��Ƿ������겢�Զ��µ��������¸�ֵ
	    int MonthDay[]={DMon,DFeb,DMon,XMon,DMon,XMon,DMon,DMon,XMon,DMon,XMon,DMon};		    
	    for(int i=0;i<MON;i++){		
		   for(int j=0;j<MonthDay[i];j++)
			   month.add(j+1);		
	    }//data init	    
	    
		//TODO:�����ض���ݵ�һ�������(��1950�꿪ʼ)��
	    OffSet=((YearIn-YearInit)+(YearIn/4-YearInit/4))%7+1;	//1900��һ��������1	
		
	}	
	void printCalender(){
		int flag=OffSet;
		int MonFlag=1;		
		for(int i=0;i<month.size();i++){
			if(month.get(i)==1){
				System.out.println("\n");
				System.out.println(MonFlag+++"��");
				System.out.println("��\tһ\t��\t��\t��\t��\t��\t");
				for(int t=0;t<flag%7;t++)
				    System.out.print("\t");
			}//�µ��·ݣ�����һ��
			System.out.print(month.get(i)+"\t");
			flag++;
			if(flag%7==0)
				System.out.print("\n");
		}
			
	}
	void main(){			
		System.out.println("current date:");
		Calendar cal = Calendar.getInstance(); 	
		System.out.println(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
	    //
		initData();
		printCalender();
		
   }
}