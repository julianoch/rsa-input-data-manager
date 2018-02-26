package org.enoch.networks.rsa.model.input.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Julian Enoch (julian.enoch@gmail.com)
 *
 */
public class RSADataGenerator {
	public static final double SLOT_WIDTH = 12.5;
	public static final double MODULATION_EFFICIENCY = 2;

	private static final int bandwidthS = 50;
	private static final int bandwidthM = 100;
	private static final int bandwidthL = 200;

	public static final boolean SYMETRICAL_TRAFFIC = true;

	private static List<int[]> demandList = new ArrayList<>();
	private static double load = 0;
	private static List<Integer> volumeList = new ArrayList<>();
	private static List<int[]> trafficNodePairList;
	private static int volumeIndex;

	public static void main(String[] args) throws Exception {
		int trafficNodesPercentage = 0;
		int vertexNumber = 27;
		double totalLoad = 20000;

		int[][] demand = generateData(trafficNodesPercentage, vertexNumber, totalLoad);

		System.out.println("demand.length " + demand.length);
		System.out.println("load " + load);
		System.out.println(Arrays.deepToString(demand));

	}

	public static int[][] generateData(int trafficNodesPercentage, int vertexNumber, double totalLoad) throws Exception {
		fillVolumeList(totalLoad);

		int nodePairNumber = vertexNumber * (vertexNumber - 1) / 2;

		trafficNodePairList = new ArrayList<>(nodePairNumber);
		for (int i = 0; i < vertexNumber; i++) {
			for (int j = i + 1; j < vertexNumber; j++) {
				trafficNodePairList.add(new int[] { i, j });
			}
		}

		while (volumeIndex < volumeList.size()) {
			Collections.shuffle(trafficNodePairList);
			int i = 0;
			while (volumeIndex < volumeList.size() && i < nodePairNumber) {
				addRequest(i++);
			}
		}

		int[][] demand = new int[demandList.size()][];
		for (int i = 0; i < demandList.size(); i++) {
			demand[i] = demandList.get(i);
		}

		return demand;
	}

	private static void fillVolumeList(double totalLoad) {
		double requestsS = Math.ceil(totalLoad * 0.7 / bandwidthS);
		double requestsM = Math.ceil(totalLoad * 0.2 / bandwidthM);
		double requestsL = Math.ceil(totalLoad * 0.1 / bandwidthL);
		for (int i = 0; i < requestsL; i++)
			volumeList.add(bandwidthL);
		for (int i = 0; i < requestsM; i++)
			volumeList.add(bandwidthM);
		for (int i = 0; i < requestsS; i++)
			volumeList.add(bandwidthS);
	}

	private static void addRequest(int i) {
		int[] nodePair = trafficNodePairList.get(i);
		int source = nodePair[0];
		int destination = nodePair[1];
		Integer volume = volumeList.get(volumeIndex++);
		int[] request = new int[3];
		request[0] = source;
		request[1] = destination;
		request[2] = (int) (volume / (SLOT_WIDTH * MODULATION_EFFICIENCY));
		load += volume;
		demandList.add(request);
	}

}
