import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class amazon {
    
    public static int i=0;
    
    //����������һ����Ʒ��ţ�����û���ظ�
    public static String last = "|";

    
    //ƥ����Ʒurl
    public static Pattern p_goods = Pattern.compile("href=\"(http://www(.+?)/dp/(.+?))\"");
    
    
    //��Ŵ���ȡ��url
    public static Queue<String> data = new LinkedList<String>();
    
    //Ҫ���뵽file��
    public static File file ;
    
    //15��ua����ã�����503�Ļ���
    public static String [] ua = {
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; Intel Mac OS X 10.6; rv:7.0.1) Gecko/20100101 Firefox/7.0.1",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 OPR/18.0.1284.68",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:7.0.1) Gecko/20100101 Firefox/7.0.1",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.9.1) Presto/2.12.388 Version/12.16",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 OPR/18.0.1284.68",
            "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) CriOS/30.0.1599.12 Mobile/11A465 Safari/8536.25",
            "Mozilla/5.0 (iPad; CPU OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4",
            "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53"
            
    };
    
    
    
    public static void action(String target) throws IOException, InterruptedException{
        
        
        //��ȫtarger������ҳ��
        if(i==0){
            
            target = "http://www.amazon.com/s/ref=nb_sb_noss?__mk_zh_CN=%E4%BA%9A%E9%A9%AC%E9%80%8A%E7%BD%91%E7%AB%99&field-keywords="+target;
            i++;
            
        }
        
        //�ȴ�������
        URL url = new URL(target);
        
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        
        conn.setRequestMethod("GET");
        
        
        Random index = new Random();
        
        String u = ua[Math.abs(index.nextInt()%15)];
        //System.out.println("us--->"+u);
        //�������ua
        conn.setRequestProperty("User-Agent",u);
        
        conn.setRequestProperty("Host","www.amazon.com");
        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Connection","keep-alive");
        
        conn.connect();
        
        InputStream in = conn.getInputStream();
        
        byte [] buf = new byte[1024];
        
        //������װ��ByteArrayOutputStream��
         ByteArrayOutputStream outStream = new ByteArrayOutputStream();
     
         int len = 0;
        
        while((len=in.read(buf))!=-1){
            
            outStream.write(buf,0,len);
        }
        in.close();
        outStream.close();
        
        String content = new String(outStream.toByteArray());
        
        
        //�ж�ҳ������
        
        Pattern pp_goods = Pattern.compile("http://www(.+?)/dp/(.+)");
        Matcher m_goods = pp_goods.matcher(target);
        
        
        //�������Ʒҳ��
        if(m_goods.find()){
            
            System.out.println("This is goods page");
            
            //��ȡ���ֺͼ۸�
            Pattern p_name = Pattern.compile("<meta name=\"description\" content=\"(.+?),");
  
            Matcher m_name = p_name.matcher(content);
            
            Pattern p_price = Pattern.compile("class=\"a-size-medium a-color-price\">��(.+?)</span>");

            Matcher m_price = p_price.matcher(content);
            
            
            //�ļ�д����
            PrintWriter pw = new PrintWriter(new FileWriter("goods.txt", true));
            
            
            while(m_name.find()){
                
                
                pw.print(m_name.group(1)+"------->");
                
            }
            while(m_price.find()){
                
                
                pw.println(m_price.group(1));
                
            }
            
           pw.close();
            
           
       
            
        }
        //���������б�ҳ����
        else{
   
            //��ȡ�������Ʒurl
            
                m_goods = p_goods.matcher(content);
            
                int count = 0;
                
                //��ȡ���url�ı��
                Pattern p_num = Pattern.compile("/dp/(.+?)/");
                
                
                while(m_goods.find()){
                
                
                    
                    String current = m_goods.group(1);
                    
                    
                    
                    Matcher m_num = p_num.matcher(current);
                    
                    String current_num = "";
                    
                    if(m_num.find()){
                        current_num = m_num.group(1);
                        //System.out.println(current_num);
                    }
                    
                    
                
                    //ȥ�أ�����������������
                        
                    //�����������һ����Ʒ��ţ��Ž���url�������
                    if(!current_num.equals(last)){
                        //System.out.println(current);
                        //System.out.println(current_num);
                        //System.out.println("goods url");
                        data.add(current);
                        last = current_num;    
                    
                    }
                
                }
                
                
                
                
        }
    }
        
    
    
    public static void main(String args[]) throws IOException, InterruptedException{
        //ֱ��������Ʒ���־ͺ���
        String target = "macbook pro";
        amazon.action(target);
        //������зǿգ���һֱ����
        while(!data.isEmpty()){       
            amazon.action(data.poll());  
            
        }
            
    }
    
        
        
        
    }