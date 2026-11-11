import java.io.*;import java.nio.file.*;
public class BufferedDesign {
 static long count(Path p,boolean buffered)throws IOException{
  long lines=0;int last=-1;try(InputStream in=buffered?new BufferedInputStream(Files.newInputStream(p)):new FileInputStream(p.toFile())){int value;while((value=in.read())!=-1){if(value=='\n')lines++;last=value;}}
  return lines+(last!=-1&&last!='\n'?1:0);
 }
 public static void main(String[] args)throws Exception{
  Path p=Path.of("line-test.txt");Files.writeString(p,"first\nsecond");Bench.require(count(p,false)==2&&count(p,true)==2,"unterminated final line counted");
  Files.writeString(p,"");Bench.require(count(p,true)==0,"empty file has zero lines");
  System.out.println("For 50,000,000 bytes: one-byte read calls=50,000,001 including EOF; 8192-byte reads approx="+((50000000L+8191)/8192+1));
 }
}
