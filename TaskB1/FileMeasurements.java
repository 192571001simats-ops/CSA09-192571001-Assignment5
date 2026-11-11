import java.nio.file.*;import java.io.*;import java.util.*;import java.time.*;import java.math.*;
public class FileMeasurements {
 static long lines(Path p,boolean buffered)throws IOException{long count=0;int last=-1;try(InputStream in=buffered?new BufferedInputStream(Files.newInputStream(p)):new FileInputStream(p.toFile())){int v;while((v=in.read())!=-1){if(v=='\n')count++;last=v;}}return count+(last!=-1&&last!='\n'?1:0);}
 static void generatedLog(Path p,int size)throws IOException{byte[] row="123456789\n".getBytes(java.nio.charset.StandardCharsets.US_ASCII);try(OutputStream out=new BufferedOutputStream(Files.newOutputStream(p))){for(int i=0;i<size/row.length;i++)out.write(row);}}
 public static void main(String[] args)throws Exception{
  Path dir=Path.of("inputs");Files.createDirectories(dir);
  for(int bytes:new int[]{1000000,10000000,50000000}){
   Path p=dir.resolve("attendance-"+bytes+".log");generatedLog(p,bytes);
   for(boolean buffered:new boolean[]{false,true})Bench.measure(buffered?"buffered_line_count":"unbuffered_line_count",bytes,()->{try{long got=lines(p,buffered);if(got!=bytes/10)throw new AssertionError("line total");Bench.sink=got;}catch(IOException e){throw new UncheckedIOException(e);}});
  }
  for(int n:new int[]{1000,10000,100000}){
   Path p=dir.resolve("passes-"+n+".csv");try(BufferedWriter writer=Files.newBufferedWriter(p)){for(int i=0;i<n;i++)writer.write(new BusPassStore.Pass("P"+i,"R"+i,"Student "+i,12,"Campus",LocalDate.of(2027,1,31),new BigDecimal("4500.00")).csv()+"\n");}
   BusPassStore store=new BusPassStore(p);Bench.measure("linear_find_last",n,()->{try{Bench.sink=store.find("P"+(n-1)).orElseThrow().route();}catch(IOException e){throw new UncheckedIOException(e);}});
  }
  System.out.println("Measured files use decimal MB; file generation is outside timed sections; OS page cache is warm");
 }
}
