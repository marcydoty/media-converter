import static org.junit.Assert.assertNotEquals;

import java.io.File;

import org.junit.Test;

import com.brightcove.zencoder.client.ZencoderClientException;
import com.marcilene.exception.EncoderException;
import com.marcilene.service.AmazonService;
import com.marcilene.service.EncoderService;
import com.marcilene.util.FileUtil;

public class TestsMediaConverter {

	EncoderService encoderService = new EncoderService();
	AmazonService amazonService = new AmazonService();
	String urlTest = "http://dinamica-sambatech.s3.amazonaws.com/sample.dv";
	
	@Test
	public void testUploadService() throws ZencoderClientException, EncoderException {

		String urlEncoder = encoderService.createJob(urlTest);
		File tempFile = FileUtil.getFile(urlEncoder);
		assertNotEquals("Erro ao enviar vídeo", "", amazonService.uploadFileEncoder(tempFile));

	}
	@Test
	public void testEncodeService() throws ZencoderClientException, EncoderException {
		EncoderService encoderService = new EncoderService();
		assertNotEquals("Erro ao tentar converter vídeo", "", encoderService.createJob(urlTest));

	}



}
