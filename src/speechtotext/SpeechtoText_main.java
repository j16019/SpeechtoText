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
		service.setUsernameAndPassword("j16019", "j16019");

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

			MySQL mysql = new MySQL();
			String text;
			double confidence;
			for(int i = 0; i < node.get("results").size(); i++){
				text = node.get("results").get(i).get("alternatives").get(0).get("transcript").asText();
				System.out.println("tarascript : " + text);
				confidence = node.get("results").get(i).get("alternatives").get(0).get("confidence").asDouble();
				System.out.println("confidence : " + confidence);
				mysql.updateImage(text, confidence);
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
