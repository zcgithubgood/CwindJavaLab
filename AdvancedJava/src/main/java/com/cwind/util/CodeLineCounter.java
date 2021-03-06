package com.cwind.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CodeLineCounter {
	static long normalLine = 0;
	static long commentLine = 0;
	static long whiteLine = 0;

	public static void countCodeLine(File rootDir){
		File[] files = rootDir.listFiles();
		for(File file : files){
			if(file.isDirectory()){
				countCodeLine(file);
			}else if(file.getName().endsWith(".java")){
				BufferedReader br = null;
				boolean bln = false;
				try{
					br = new BufferedReader(new FileReader(file));
					String line = "";
					try{
						while((line=br.readLine()) != null) {
							line = line.trim();
							if(line.matches("^[\\s&&[^\\n]]*$")){
								whiteLine+=1;
							}else if(line.startsWith("/*")&&!line.endsWith("*/")){
								commentLine+=1;
								bln = true;
							}else if(bln){
								commentLine+=1;
								if(line.endsWith("*/")){
									bln=false;
								}
							}else if(line.startsWith("/*")&&line.endsWith("*/")){
								commentLine+=1;
							}else if(line.startsWith("//")){
								commentLine+=1;
							}else{
								normalLine+=1;
							}
						}
					}catch(IOException e){
						e.printStackTrace();
					}
					
				}catch(FileNotFoundException e){
					e.printStackTrace();
				}finally{
					if(br!=null){
						try{
							br.close();
							br=null;
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	
	
	public static void main(String[] args){
		String springcore = "..\\..\\References\\spring-framework\\spring-core";
		String springaop = "..\\..\\References\\spring-framework\\spring-aop";
		String ehcached3 = "..\\..\\References\\ehcache3";
		String netty = "..\\..\\References\\netty";
		String jetty = "..\\..\\References\\jetty.project";
		
		String designpatterns = "..\\..\\java-design-patterns";
		String cwindjavalab = "..\\..\\CwindJavaLab";
		String familybilling = "..\\..\\..\\LunaWorkspace\\FamilyBilling";
		String devicemanager = "..\\..\\..\\LunaWorkspace\\DeviceManager";
		
		File rootDir = new File(cwindjavalab);
		countCodeLine(rootDir);

		System.out.println("有效行数： "+normalLine);
		System.out.println("注释行数： "+commentLine);
		System.out.println("空白行数： "+whiteLine);
	}
}
