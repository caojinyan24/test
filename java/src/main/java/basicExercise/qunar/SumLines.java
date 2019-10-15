package basicExercise.qunar;

//import java.io.BufferedReader;
import java.io.*;
//import java.lang.*;
//import java.util.ArrayList;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;


 public class SumLines {
	public void Test(String[] temp) throws IOException{
		//GetLines1 g=new GetLines1();
		//System.out.println(g.SumLines());
		//System.out.println("\n");
		CalChar cc=new CalChar();
		//cc.Write("StringUtils5.java");
		cc.calDir(temp);	
		
		CalClass t=new CalClass();
		t.calDir(temp);
	}
}

/**
 * ͳ��һ��Java�ļ�����Ч������
1)��Ч����������
2)�����Ǵ�����ж���ע�͵����
 * @author cjy
 *
 */	
class GetLines{
	int SumLines(){
		int lines=0;
		try{
			BufferedReader in =new BufferedReader(new FileReader(new File("StringUtils1.java").getAbsoluteFile()));
		    try{
			   String s;
			   while((s=in.readLine())!=null){
				   if(s.matches("[ ]*"))
					   continue;
				   if(!s.startsWith("/*")&&!s.startsWith("//")){
					   lines++;
				  }
			  }
		   }finally{			   
			   in.close();
		   }
		
		}catch(IOException e){
			throw new RuntimeException(e);
		}

		return lines;
	}
}
 /*
 * ͳ��һ��Java�ļ�����Ч������
1)��Ч����������
2)����*����//���ж���ע�͵����
 * @author cjy
 *   //
 //*/	
class GetLines1{
	int lines=0;
	boolean iscommented=false;
	
	String Process(String s){
		if(!iscommented&&s.contains("//")){
			s=s.substring(0,s.indexOf("//"));
			
		}
		//iscommented&&s.contains("*/")
		 
		else if(iscommented&&s.contains("*/")){
			s=s.substring(s.indexOf("*/")+2);
			iscommented=false;
		}
		//!iscommented&&!s.contains("*/")
		
		  //!iscommented&&s.contains("*/")
		
		
		else if(!iscommented&&s.contains("/*")&&s.contains("*/")&&(s.indexOf("/*")-s.indexOf("*/"))>1){
			iscommented=false;
			s=s.replace(s.substring(s.indexOf("/*"),s.indexOf("*/"))," ");
		}
		else if(!iscommented&&s.contains("/*")&&!s.substring(s.indexOf("/*")+2).contains("*/")){//�ų�/*/�����
			iscommented=true;
			s=s.substring(0,s.indexOf("/*"));
		}			
		return s;
	}
	
	int SumLines(){		
		try{
			BufferedReader in =new BufferedReader(new FileReader(new File("StringUtils1.java").getAbsoluteFile()));
			//boolean iscommented=false;
		    try{
			   String s;
			   //String stemp;
			   while((s=in.readLine())!=null){				   
				   //����
				  if(s.matches("[ ]*")){
					   continue;//���� 				  
					}
				 
				  if(iscommented&&!s.contains("*/"))
						continue;
				  else{//iscommented&&s.contains("*/")
					  //!iscommented&&!s.contains("*/")
					  //!iscommented&&s.contains("*/")
					  
					  do{
						
						  s=Process(s);
					  }while(s.contains("/*"));
					  
					  if(!s.matches("[ ]*")&&!s.startsWith("//")){//������/�� 
						  lines++;
					  }
					  
				  }				  
			  }
		   }finally{			   
			   in.close();
		   }
		
		}catch(IOException e){
			throw new RuntimeException(e);
		}

		return lines;
	}
}
/**
 * ͳ�ƣ�
1)����һ��Ŀ¼�£������ļ������֡���ĸ(��Сд������)�����֡��ո�ĸ�����������
2)���������д�뵽�ļ��С�
�ļ���ʽ���£�
���֣�198213��
��ĸ��18231��
���ӣ�1238123��
�ո�823145��
������99812��
����0��123��
����1��1214��
����2��23423��
����
��ĸA��754456��������B��7567��
����C��456456��
......
	
 */

/**
 * �ж��ַ��Ƿ�Ϊ����
 * public static boolean isChinese(char a){
	int v = (int)a; 
	return (v >=19968 && v <= 171941); 
	}
 */
class CalChar{//������ͨ��ѭ����0-9��a-Z�ļ��Ȳ��ȥ������ֱ����map�е�key������ֵ����
	
	Map<String,Integer> list=new LinkedHashMapF<String,Integer>();
	  CalChar(){
		list.put("����",0);
		list.put("��ĸ",0);
		list.put("����", 0);
		list.put("�ո�", 0);
		list.put("����", 0);
		for(int i=0;i<=9;i++){
			list.put("����"+(char)(i+'0'), 0);
		}
		for(int i=0;i<26;i++){
			char temp=(char) ('a'+i);
			list.put("��ĸ"+temp, 0);
		}
		for(int i=0;i<26;i++){
			char temp=(char)('A'+i);
			list.put("��ĸ"+temp, 0);
		}
	}
	//TODO���Ժ��ֵı����Żᱻʶ��Ϊ����
	void Read(String filename)throws IOException{
		BufferedReader in=new BufferedReader(new FileReader(filename));
		String s;
		while((s=in.readLine())!=null){
			list.put("����",list.get("����").intValue()+1);
			StringReader sr=new StringReader(s);
			int cc=0;
			while((cc=sr.read())!=-1){//c�Ƕ�����ַ����ڴ˴ζ��ַ����ж�
				
				if(cc>=19968&&cc<=171941){				
						list.put("����",list.get("����").intValue()+1);					
				}//�ж��Ǻ���
				char c=(char)cc;
				if(c>='0'+0&&c<='0'+9){
					list.put("����",list.get("����").intValue()+1);
					list.put("����"+c, list.get("����"+c).intValue()+1);
					
				}//�ж�������
				else if((c>='a'&&c<='z')||(c>='A'&&c<='Z')){
					list.put("��ĸ",list.get("��ĸ").intValue()+1);
					list.put("��ĸ"+c, list.get("��ĸ"+c).intValue()+1);				
				}//�ж�����ĸ				
				else if(c==' '){
					list.put("�ո�", list.get("�ո�").intValue()+1);
				}//�ҵ��ո�
			}
				
		}
		in.close();
		
	}
	@SuppressWarnings("resource")
	//TODO����֪����ô��map�е�����˳�����
	void Write(String filename)throws IOException{
		Read(filename);
		String file="out.txt";
		PrintWriter out=new PrintWriter(file);
//		out.println("����: "+list.get("����").intValue()+"��");
//		out.println("��ĸ: "+list.get("��ĸ").intValue()+"��");
//		out.println("����: "+list.get("����").intValue()+"��");
//		out.println("�ո�: "+list.get("�ո�").intValue()+"��");
//		out.println("����: "+list.get("����").intValue()+"��");		
//		for(int i=0;i<=9;i++){			
//			out.println("����"+i+": "+list.get("����"+i).intValue()+"��");
//		}
//		for(int i=0;i<26;i++){
//			char temp=(char) ('a'+i);
//			out.println("��ĸ"+temp+": "+list.get("��ĸ"+temp).intValue()+"��");
//		}
//		for(int i=0;i<26;i++){
//			char temp=(char)('A'+i);
//			out.println("��ĸ"+temp+": "+list.get("��ĸ"+temp).intValue()+"��");
//		}
		out.print(list);
		out.close();
	}
   void calDir(String[] dirFilter){
    	File path=new File(".");
    	String[] flist;
    	if(dirFilter.length==0)
    		flist=path.list();
    	else
    		flist=path.list(new DirFilter(dirFilter[0]));
    	for(String nameTemp:flist){
    		try{
    			Write(nameTemp);
    		}catch(IOException e){
    			throw new RuntimeException( e);
    		}
    	}
    		
    }
	
    class DirFilter implements FilenameFilter{
		private Pattern pattern;
		DirFilter(String regex){
			pattern=Pattern.compile(regex);
		}
		public boolean accept(File dir,String name){
			return pattern.matcher(name).matches();
		}
	}
}
class LinkedHashMapF<K,V> extends LinkedHashMap<K,V>{
	public String toString() {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
      
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append(": ");
            sb.append(value == this ? "(this Map)" : value);
            sb.append("��\r\n ");
            if (! i.hasNext())
                return sb.append("\r\n").toString();
            //sb.append(',').append(' ');
        }
    }

}  
/**
 * ����ָ����ĿĿ¼�£�������Ϊ��javaԴ�ļ�Ŀ¼)�У�ͳ�Ʊ�import�����ࡣ
 * //�����ǰ��������������
 *   //������import���ֻ��е����
 * */
class CalClass{
	Map<String,Integer> map=new HashMap<String,Integer>();//�������г��ֵ���ʹ���
	List<String> list=new ArrayList<String>();//������ִ���������
	int max=0;
	void readFile(String filename)throws IOException{
		BufferedReader in=new BufferedReader(new FileReader(filename));
		String s;
		while((s=in.readLine())!=null){
			if(s.contains("import")){
				s=s.substring(s.indexOf("import")+6,s.indexOf(";"));
				String stemp[]=s.split(".");
				for(String st:Arrays.asList(stemp)){
					if(st.matches("[A-Z]+")){
						int numtemp=map.get(st)==null?1:map.get(st)+1;
						map.put(st, numtemp);
						if(numtemp>max){
							list.removeAll(list);
							list.add(st);
							max=numtemp;
						}
						else if(numtemp==max){
							list.add(st);
						}
					}
				}
				
			}
				
		}
		in.close();
		
	}
	void calDir(String[] dirFilter){
    	File path=new File(".");
    	String[] flist;
    	if(dirFilter.length==0)
    		flist=path.list();
    	else
    		flist=path.list(new DirFilter(dirFilter[0]));
    	for(String nameTemp:flist){
    		try{
    			readFile(nameTemp);
    		}catch(IOException e){
    			throw new RuntimeException( e);
    		}
    	}
    	System.out.println(list);
    	System.out.println(max);
    		
    }
	class DirFilter implements FilenameFilter{
		private Pattern pattern;
		DirFilter(String regex){
			pattern=Pattern.compile(regex);
		}
		public boolean accept(File dir,String name){
			return pattern.matcher(name).matches();
		}
	}
	
}