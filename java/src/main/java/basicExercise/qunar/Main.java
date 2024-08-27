package basicExercise.qunar;


import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


public class Main {
	public static void main(String[] args)throws IOException {		
		
		Replace rc=new Replace();
		rc.Test();
		//SumLines sl=new SumLines();
		//sl.Test(args);		
		//CalClass t=new CalClass();
		//t.calDir(args);
		//Calculate c=new Calculate();
		//c.Test();
 }
}
class CalClass1{
	Map<String,Integer> map=new HashMap<String,Integer>();//�������г��ֵ���ʹ���
	List<String> list=new ArrayList<String>();//������ִ���������
	int max=0;
	void readFile(String filename)throws IOException{
		BufferedReader in=new BufferedReader(new FileReader(filename));
		String s;
		while((s=in.readLine())!=null){
			if(s.contains("import")){
				s=s.substring(s.indexOf("import")+6,s.indexOf(";"));				
				String[] stemp=s.split("\\.");
				boolean t=s.contains(".");
				for(String st:Arrays.asList(stemp)){
					if(st.matches("[A-Z].*")){
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