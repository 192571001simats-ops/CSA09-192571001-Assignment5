import java.io.*;import java.nio.file.*;import java.time.*;import java.math.*;import java.util.*;
public class RecordLayouts {
 public static void main(String[] args)throws Exception{
  var p=new BusPassStore.Pass("P1001","192571001","Athisaya, U",12,"Anna Nagar",LocalDate.of(2027,1,31),new BigDecimal("4500.00"));
  Path dir=Path.of("inputs");Files.createDirectories(dir);Path f=dir.resolve("passes.csv");Files.deleteIfExists(f);var store=new BusPassStore(f);store.add(p);
  Bench.require(store.find("P1001").orElseThrow().equals(p),"quoted comma round trip");Bench.require(store.route(12).size()==1,"route listing");
  try(DataOutputStream out=new DataOutputStream(Files.newOutputStream(dir.resolve("passes.bin")))){out.writeUTF("BP1");out.writeUTF(p.id());out.writeUTF(p.reg());out.writeUTF(p.name());out.writeInt(p.route());out.writeUTF(p.stop());out.writeLong(p.validity().toEpochDay());out.writeLong(p.amount().movePointRight(2).longValueExact());}
  try(DataInputStream in=new DataInputStream(Files.newInputStream(dir.resolve("passes.bin")))){Bench.require(in.readUTF().equals("BP1"),"binary format version");var copy=new BusPassStore.Pass(in.readUTF(),in.readUTF(),in.readUTF(),in.readInt(),in.readUTF(),LocalDate.ofEpochDay(in.readLong()),BigDecimal.valueOf(in.readLong(),2));Bench.require(copy.equals(p),"binary fields round trip");}
  System.out.println(p.csv());System.out.println("Binary bytes="+Files.size(dir.resolve("passes.bin")));
 }
}
