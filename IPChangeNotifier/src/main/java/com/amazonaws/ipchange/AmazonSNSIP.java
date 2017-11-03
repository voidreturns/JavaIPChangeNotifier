package com.amazonaws.ipchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

public class AmazonSNSIP {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String fileName = System.getProperty("user.dir") + "/IP_Change.txt";
		FileReader fileReader = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fileReader);
		try {

			String ip = br.readLine();
			FileInIP fip = new FileInIP();
			fip.storeIP();
			SendNotification(ip, fip.GetPublicIP());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			br.close();
		}

	}

	public static void SendNotification(String ip, String IP) {
		System.out.println("Sending Notification");
		System.out.println("Old Public IP : " + ip);
		System.out.println("Current Public IP : " + IP);
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

		System.out.println("Current Date: " + ft.format(dNow));
		if (ip.equals(IP))
			System.out.println("IP not Changed SMS/EMAIL not triggered");
		else {
			
			AmazonSNS sns = AmazonSNSClient.builder().withRegion("us-east-1").build();
			System.out.println("Changed");
			System.out.println();
			sns.publish("YOUR AWS SNS TOPIC ARN ",
					ft.format(dNow) + " PUBLIC IP CHANGED FROM : " + ip + " TO " + IP, "IP CHANGED");
			
		}
	}

}
