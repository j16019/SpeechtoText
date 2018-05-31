package speechtotext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class SpeechtoText_main {
	public static void main(String[] args){
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("3e012ee8-cb40-4d37-bdac-a00a4d9e4a6d", "Av4kalF5NJq4");

		File audio = new File("audio/sample1.wav");
		RecognizeOptions options = null;
		try{
			options = new RecognizeOptions.Builder()
					.model("ja-JP_BroadbandModel")
					.audio(audio)
					.contentType(RecognizeOptions.ContentType.AUDIO_WAV)
					.build();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		SpeechRecognitionResults transcript = service.recognize(options).execute();

		System.out.println(transcript);

		String s = String.valueOf(transcript);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = null;
		try {
			node = mapper.readTree(s);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		String alternatives_transcript = node.get("results").get(0).get("alternatives").get(0).get("transcript").asText();
        System.out.println("alternatives_transcript : " + alternatives_transcript);
        double alternatives_confidence = node.get("results").get(0).get("alternatives").get(0).get("confidence").asDouble();
        System.out.println("alternatives_transcript : " + alternatives_confidence);
	}

}
