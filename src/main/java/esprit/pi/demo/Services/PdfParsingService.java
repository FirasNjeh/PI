package esprit.pi.demo.Services;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.stereotype.Service;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
@Service
public class PdfParsingService {
    public Object IfYouNeedSimplicity() {



        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        Document document = loadDocument("C:\\Users\\youss\\OneDrive\\Desktop\\modele_de_facture.pdf", new ApachePdfBoxDocumentParser());
        ingestor.ingest(document);

        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
                .chatLanguageModel(OpenAiChatModel.withApiKey("demo"))
                .retriever(EmbeddingStoreRetriever.from(embeddingStore, embeddingModel))
                .build();

        String answer = chain.execute("extract Total TTC,i insist that i only just want what you write to be a number in a form of double in java language");
        answer=answer.replaceAll("[^0-9.,]", "");
        answer=answer.replace(",",".");
        System.out.println(answer);
        double test=10;
        try {
            test=Double.parseDouble(answer);
        }catch (NumberFormatException e) {
            System.out.println("La chaîne ne représente pas un nombre valide.");
        }

        return test;
    }
}
