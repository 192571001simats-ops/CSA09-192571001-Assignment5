import java.io.*;import java.nio.file.*;import java.net.*;import java.util.*;import java.sql.*;import javax.tools.*;
public class SerializationStudy {
 static class Resident implements Serializable{private static final long serialVersionUID=1L;String name="Athisaya U",reg="192571001",phone;transient String password="example-only";transient Connection connection;}
 static class Room implements Serializable{private static final long serialVersionUID=1L;List<Resident> residents=new ArrayList<>();}
 static class Hostel implements Serializable{private static final long serialVersionUID=1L;List<Room> rooms=new ArrayList<>();}
 static byte[] encode(Object value)throws IOException{var bytes=new ByteArrayOutputStream();try(var out=new ObjectOutputStream(bytes)){out.writeObject(value);}return bytes.toByteArray();}
 static Class<?> version(Path folder,int uid,boolean phone)throws Exception{
  Files.createDirectories(folder);String code="import java.io.Serializable; public class ResidentVersion implements Serializable {private static final long serialVersionUID="+uid+"L; public String name=\"Arun\";"+(phone?" public String phone;":"")+"}";
  Path src=folder.resolve("ResidentVersion.java");Files.writeString(src,code);int result=ToolProvider.getSystemJavaCompiler().run(null,null,null,"-d",folder.toString(),src.toString());if(result!=0)throw new AssertionError();return new URLClassLoader(new URL[]{folder.toUri().toURL()},null).loadClass("ResidentVersion");
 }
 static Object decode(byte[] bytes,Class<?> type)throws Exception{try(ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(bytes)){@Override protected Class<?> resolveClass(ObjectStreamClass desc)throws IOException,ClassNotFoundException{return desc.getName().equals("ResidentVersion")?type:super.resolveClass(desc);}}){return in.readObject();}}
 public static void main(String[] args)throws Exception{
  Hostel h=new Hostel();Room r=new Room();Resident resident=new Resident();r.residents.add(resident);h.rooms.add(r);byte[] bytes=encode(h);
  Hostel copy;try(var in=new ObjectInputStream(new ByteArrayInputStream(bytes))){copy=(Hostel)in.readObject();}
  Bench.require(copy.rooms.get(0).residents.get(0).password==null&&copy.rooms.get(0).residents.get(0).connection==null,"transient secrets and live connections omitted");
  Class<?> old=version(Path.of("versions/v1"),1,false),compatible=version(Path.of("versions/v2"),1,true),incompatible=version(Path.of("versions/v3"),2,true);byte[] saved=encode(old.getConstructor().newInstance());Object next=decode(saved,compatible);
  Bench.require(compatible.getField("phone").get(next)==null,"old file new phone defaults to null with compatible UID");
  try{decode(saved,incompatible);throw new AssertionError();}catch(InvalidClassException e){System.out.println("PASS UID mismatch rejected: "+e.getMessage());}
  System.out.println("Hostel graph serialized bytes="+bytes.length);
 }
}
