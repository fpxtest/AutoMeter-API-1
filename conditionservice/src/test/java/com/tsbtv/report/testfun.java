package com.tsbtv.report;


import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.CharBuffer;
import java.util.*;
import javax.tools.*;


/**
 * Created by fanseasn on 2021/3/21.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2021/3/21
*/
public class testfun {


    public static void main(String[] args) throws Exception {



        boolean f1=true;
        boolean f2=false;
        boolean f3=true;
        if(f1&f2&f3)
        {
            System.out.println("111");
        }
        else
        {
            System.out.println("222");
        }
//        String javaSrc = "import com.tsbtv.report.teststatic;"+
//                "import com.tsbtv.report.MD5Util;"+
//                "public class TestClass { " +
//                "public void sayHello() {" +
//                "String res= MD5Util.encodeMD5Hex(\"Season\");"+
//                "System.out.println(res);" +
//                "}" +
//                "public int add(int a,int b){" +
//                "return a+b;" +
//                "}" +
//                "}";
//        Map<String, byte[]> results=new HashMap<>();
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
//        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
//            JavaFileObject javaFileObject = manager.makeStringSource("TestClass.java", javaSrc);
//            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
//            if (task.call()) {
//                results = manager.getClassBytes();
//            }
//        }
//
//        MemoryClassLoader classLoader=new MemoryClassLoader(results);
//
//        Class clazz = classLoader.loadClass("TestClass");
//        Object object = clazz.newInstance();
//        Method method = clazz.getMethod("sayHello");
//        method.invoke(object);

    }

    public static List<List<String>> SplitList(List<String> sList, int num) {

        if(sList.size()<num)
        {
            num=sList.size();
        }

        List<List<String>> eList = new ArrayList<List<String>>();
        List<String> gList;

        int size = (sList.size()) / num;
        int size2 = (sList.size()) % num;
        int j = 0;
        int xx = 0;
        for (int i = 0; i < num; i++) {
            gList = new ArrayList<String>();

            for (j = xx; j < (size + xx); j++) {
                gList.add(sList.get(j));
            }
            xx = j;
            eList.add(gList);
        }
        if (size2 != 0) {
            //gList = new ArrayList<String>();
            for (int y = 1; y < size2 + 1; y++) {
                eList.get(eList.size()-1).add(sList.get(sList.size() - y));
            }
        }
        return eList;
    }

    public static   ArrayList<ArrayList<String>> splitList(ArrayList<String> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        int sourceSize = source.size();
        int size;
        if (source.size() % n == 0) {
            size = source.size() / n;
        } else {
            size = (source.size() / n) + 1;
        }
        for (int i = 0; i < size; i++) {
            ArrayList<String> subset = new ArrayList<>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }
}


@SuppressWarnings("unchecked")
final class MemoryJavaFileManager extends ForwardingJavaFileManager {

    /**
     * Java source file extension.
     */
    private final static String EXT = ".java";

    private Map<String, byte[]> classBytes;

    public MemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
        classBytes = new HashMap<String, byte[]>();
    }

    public Map<String, byte[]> getClassBytes() {
        return classBytes;
    }

    public void close() throws IOException {
        classBytes = new HashMap<String, byte[]>();
    }

    public void flush() throws IOException {
    }

    /**
     * A file object used to represent Java source coming from a string.
     */
    private static class StringInputBuffer extends SimpleJavaFileObject {
        final String code;

        StringInputBuffer(String name, String code) {
            super(toURI(name), Kind.SOURCE);
            this.code = code;
        }

        public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
            return CharBuffer.wrap(code);
        }

        public Reader openReader() {
            return new StringReader(code);
        }
    }

    /**
     * A file object that stores Java bytecode into the classBytes map.
     */
    private class ClassOutputBuffer extends SimpleJavaFileObject {
        private String name;

        ClassOutputBuffer(String name) {
            super(toURI(name), Kind.CLASS);
            this.name = name;
        }

        public OutputStream openOutputStream() {
            return new FilterOutputStream(new ByteArrayOutputStream()) {
                public void close() throws IOException {
                    out.close();
                    ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                    classBytes.put(name, bos.toByteArray());
                }
            };
        }
    }

    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location,
                                               String className,
                                               JavaFileObject.Kind kind,
                                               FileObject sibling) throws IOException, IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            return new ClassOutputBuffer(className);
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    static JavaFileObject makeStringSource(String name, String code) {
        return new StringInputBuffer(name, code);
    }

    static URI toURI(String name) {
        File file = new File(name);
        if (file.exists()) {
            return file.toURI();
        } else {
            try {
                final StringBuilder newUri = new StringBuilder();
                newUri.append("mfm:///");
                newUri.append(name.replace('.', '/'));
                if (name.endsWith(EXT)) newUri.replace(newUri.length() - EXT.length(), newUri.length(), EXT);
                return URI.create(newUri.toString());
            } catch (Exception exp) {
                return URI.create("mfm:///com/sun/script/java/java_source");
            }
        }
    }
}

class MemoryClassLoader extends URLClassLoader {

    Map<String, byte[]> classBytes = new HashMap<String, byte[]>();

    public MemoryClassLoader(Map<String, byte[]> classBytes) {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
        this.classBytes.putAll(classBytes);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] buf = classBytes.get(name);
        if (buf == null) {
            return super.findClass(name);
        }
        classBytes.remove(name);
        return defineClass(name, buf, 0, buf.length);
    }
}




