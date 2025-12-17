package Media;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class ScreenRecorder {

    // ThreadLocal ensures that parallel tests do not overwrite each other's recorders
    private static final ThreadLocal<org.monte.screenrecorder.ScreenRecorder> screenRecorder = new ThreadLocal<>();

    public static void startRecording(String methodName) throws Exception {
        File file = new File("test-recordings/");
        if (!file.exists()) {
            file.mkdirs();
        }

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(0, 0, screen.width, screen.height);

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        // Initialize the SpecializedScreenRecorder (defined below)
        org.monte.screenrecorder.ScreenRecorder recorder = new SpecializedScreenRecorder(
                gc,
                captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f, KeyFrameIntervalKey, 15),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                null,
                file,
                methodName // Pass the test name here
        );

        screenRecorder.set(recorder);
        screenRecorder.get().start();
    }

    public static File stopRecording() throws Exception {
        org.monte.screenrecorder.ScreenRecorder recorder = screenRecorder.get();
        if (recorder != null) {
            recorder.stop();
            // Return the specific file created for this thread
            if (!recorder.getCreatedMovieFiles().isEmpty()) {
                return recorder.getCreatedMovieFiles().get(0);
            }
        }
        return null;
    }

    /**
     * Inner class to override the movie file creation.
     * This allows us to name the file exactly what we want (e.g., "testLogin.avi")
     */
    private static class SpecializedScreenRecorder extends org.monte.screenrecorder.ScreenRecorder {
        private String name;

        public SpecializedScreenRecorder(GraphicsConfiguration cfg,
                                         Rectangle captureArea, Format fileFormat, Format screenFormat,
                                         Format mouseFormat, Format audioFormat, File movieFolder,
                                         String name) throws IOException, AWTException {
            super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
            this.name = name;
        }

        @Override
        protected File createMovieFile(Format fileFormat) throws IOException {
            if (!movieFolder.exists()) {
                movieFolder.mkdirs();
            } else if (!movieFolder.isDirectory()) {
                throw new IOException("\"" + movieFolder + "\" is not a directory.");
            }

            // Generate filename based on method name + .avi
            return new File(movieFolder, name + "." + Registry.getInstance().getExtension(fileFormat));
        }
    }
}