package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Reglamento extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglamento);

        pdfView = findViewById(R.id.PdfLaPaz);
        pdfView.fromAsset("reglamento_la_paz.pdf").load();
    }
}
