package com.simulator;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;
import net.minidev.json.JSONObject;

public class ExternalServiceSimulator {

  private static final String CREATE_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON
      = ResourceUtility.generateStringFromResource("requestJson/DemoAccountViewModel.json");

  public static void main(String[] args) throws IOException {

    final String accountNumber = createAccount();
    //final String accountNumber = "406e1ad7-644d-4d71-bff4-5dbbd27eeb3a"; //Can enter an existing user you've created here to simulate transactions

    final String[] transactionTypes = {"CHECK", "DEBIT"};
    final String[] descriptions = {"Ebay", "Gumtree", "Market", "Bank Stuff", "House Stuff"};

    for (int i = 0; i < 50; i++) {
      final JSONObject json = new JSONObject();
      json.put("accountNumber", accountNumber);
      json.put("amount", new Random().nextInt(1000));
      json.put("type", transactionTypes[new Random().nextInt(transactionTypes.length)]);
      json.put("description", descriptions[new Random().nextInt(descriptions.length)]);

      final String randomisedJsonMessage = json.toString();
      externalServicePutProcessing(randomisedJsonMessage);
    }
  }

  private static String externalServicePutProcessing(final String jsonInputString) {
    final URL url;
    HttpURLConnection con = null;
    try {
      url = new URL("http://localhost:8080/external/");
      con = (HttpURLConnection) url.openConnection();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      con.setRequestMethod("PUT");
      con.setRequestProperty("Content-Type", "application/json; utf-8");
      con.setRequestProperty("Accept", "application/json");
    } catch (ProtocolException e) {
      e.printStackTrace();
    }

    con.setDoOutput(true);

    try (final OutputStream os = con.getOutputStream()) {
      final byte[] input = jsonInputString.getBytes("utf-8");
      os.write(input, 0, input.length);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String responseMessage = null;
    try (final BufferedReader br = new BufferedReader(
        new InputStreamReader(con.getInputStream(), "utf-8"))) {
      final StringBuilder response = new StringBuilder();
      String responseLine = null;
      while ((responseLine = br.readLine()) != null) {
        response.append(responseLine.trim());
      }
      responseMessage = response.toString();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      try {
        if (con.getResponseCode() == 500) {
          responseMessage = "Insufficient Funds"; //Print Insufficent funds since con can't return message text that the user would normally see
        }
      } catch (IOException ex) {
      }
      e.printStackTrace();
    }

    System.out.println("Transaction ID: " + responseMessage);
    return responseMessage;

  }

  private static String createAccount() throws IOException {
    final URL url = new URL("http://localhost:8080/account/");

    final HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json; utf-8");
    con.setRequestProperty("Accept", "application/json");
    con.setDoOutput(true);

    final String jsonInputString = CREATE_BANK_ACCOUNT_VIEW_MODEL_VALID_JSON;

    try (final OutputStream os = con.getOutputStream()) {
      final byte[] input = jsonInputString.getBytes("utf-8");
      os.write(input, 0, input.length);
    }

    try (final BufferedReader br = new BufferedReader(
        new InputStreamReader(con.getInputStream(), "utf-8"))) {
      final StringBuilder response = new StringBuilder();
      String responseLine = null;
      while ((responseLine = br.readLine()) != null) {
        response.append(responseLine.trim());
      }
      final String fullResponse = response.toString();
      final String accountNumber = fullResponse.substring(fullResponse.indexOf(":") + 2, fullResponse.indexOf(",") - 1);

      System.out.println("Account Number:" + accountNumber);
      return accountNumber;
    }
  }

  private static class ResourceUtility {

    public static String generateStringFromResource(final String path) {
      String resourceString = "";
      try {
        resourceString = Resources.toString(Resources.getResource(path), Charsets.UTF_8);
      } catch (IOException ex) {
        System.out.println("Can not retrieve resource entity");
      }
      return resourceString;
    }
  }
}
