package com.dipisoft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> processList;

	    if (args.length > 0) {
            processList = generateProcessList(Byte.parseByte(args[0]), Byte.parseByte(args[1]), args[2]);
        } else {
            Scanner scan = new Scanner(System.in);

            System.out.println("1 - Google Chrome\n2 - Mozilla Firefox\n3 - Microsoft Edge");
            System.out.print("Select a browser (type the number): ");
            byte browser = scan.nextByte();

            System.out.print("\nWebsite URL: ");
            String url = scan.next();

            System.out.print("\nHow many windows?: ");
            byte reps = scan.nextByte();

            processList = generateProcessList(reps, browser, url);

            scan.close();
        }

        int aliveProcessCount = processList.size();
        System.out.println("\nNumber of processes: " + aliveProcessCount);

        while (aliveProcessCount > 0) {
            for (Process pr:processList) {
                if (!(pr.isAlive())) {
                    aliveProcessCount--;
                    try {
                        int exitCode = pr.waitFor();
                        System.out.println("One process ended, exit code = " + exitCode + ", remaning: " + aliveProcessCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("\n>> Execution ended.");
    }

    private static ArrayList<Process> generateProcessList(byte reps, byte browser, String url) {
        ProcessBuilder pb = new ProcessBuilder();

        switch (browser) {
            case 1:
                pb.command("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", "--new-window", url);
                break;
            case 2:
                pb.command("C:\\Program Files\\Mozilla Firefox\\firefox.exe", "--new-window", url);
                break;
            case 3:
                pb.command("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe", "--new-window", url);
                break;
        }

        File dir = new File(".." + File.separator + ".." + File.separator + "..");
        pb.directory(dir);

        ArrayList<Process> processList = new ArrayList<Process>();
        for (int i = 0; i < reps; i++) {
            try {
                processList.add(pb.start());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return processList;
    }
}
