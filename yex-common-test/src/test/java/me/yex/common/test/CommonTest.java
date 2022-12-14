package me.yex.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yexy
 * @description
 */
@RunWith(JUnit4.class)
public class CommonTest {

    @Test
    public void test() {
        System.out.println("test");
    }

//
//    @Test
//    public void optimizeString() {
//        String str1 = "abc";
//        String str2 = new String("abc");
//        String str3 = str2.intern();
//
//        System.out.println(str1 == str2);   //false
//        System.out.println(str2 == str3);   //false
//        System.out.println(str1 == str3);   //true
//    }

//    @Test
//    public void regx() {
////        String str = "abbc";
////        String pattern1 = "ab{1,3}c";
////        String pattern2 = "ab{1,3}?c";
////        String pattern3 = "ab{1,3}+c";
////        String pattern4 = "ab{1,3}+bc";
////        String pattern5 = "ab{1,3}?bc";
////        String pattern6 = "ab{1,3}bc";
////
////
////        Pattern r1 = Pattern.compile(pattern1);
////        Pattern r2 = Pattern.compile(pattern2);
////        Pattern r3 = Pattern.compile(pattern3);
////        Pattern r4 = Pattern.compile(pattern4);
////        Pattern r5 = Pattern.compile(pattern5);
////        Pattern r6 = Pattern.compile(pattern6);
////
////        System.out.println(r1.matcher(str).matches());
////        System.out.println(r2.matcher(str).matches());
////        System.out.println(r3.matcher(str).matches());
////        System.out.println(r4.matcher(str).matches());
////        System.out.println(r5.matcher(str).matches());
////        System.out.println(r6.matcher(str).matches());
//
//        String text = "<input high=\"20\" weight=\"70\">test</input>";
//        String reg = "(<input.*?>)(.*?)(</input>)";
//        Pattern p = Pattern.compile(reg);
//        Matcher m = p.matcher(text);
//        while (m.find()) {
//            System.out.println(m.group(0));//整个匹配到的内容
//            System.out.println(m.group(1));//(<input.*?>)
//            System.out.println(m.group(2));//(.*?)
//            System.out.println(m.group(3));//(</input>)
//        }
//    }
//
//    @Test
//    public void list() {
//
//    }
}