package com.amazonaws.ipchange;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInIP {

	public String GetPublicIP() throws MalformedURLException, IOException {

		String url = "http://checkip.dyndns.org";
		URL conn = new URL(url);
		URLConnection webcon = conn.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(webcon.getInputStream()));
		String ip = br.readLine();
		String[] a = ip.split(":");
		String a2 = a[1].substring(1);
		String[] a3 = a2.split("<");
		String a4 = a3[0];
		return a4;

	}

	public void storeIP() throws FileNotFoundException, IOException {
		System.out.print(System.getProperty("user.dir"));
		String fileName = System.getProperty("user.dir") + "/IP_Change.txt";
		String iplog = System.getProperty("user.dir") + "/IP_Log.txt";
		FileWriter fileWriter = new FileWriter(fileName);
		FileWriter fw = new FileWriter(iplog, true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		BufferedWriter bwip = new BufferedWriter(fw);
		PrintWriter outip = new PrintWriter(bwip);
		try {
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

			// System.out.println("Current Date: " + ft.format(dNow));
			outip.println(ft.format(dNow) + " IP Address : " + GetPublicIP());
			bw.write(GetPublicIP());
			bw.close();
			outip.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		} finally {
			bw.close();
			outip.close();
		}

	}
}
