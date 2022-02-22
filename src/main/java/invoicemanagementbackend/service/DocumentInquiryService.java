package invoicemanagementbackend.service;

import invoicemanagementbackend.model.Biller;
import invoicemanagementbackend.model.Document;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DocumentInquiryService {

    public List<Document> documentInquiry() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference documentReference = firestore.collection("documents");
        ApiFuture<QuerySnapshot> future = documentReference.get();
        QuerySnapshot document = future.get();

        if(!document.isEmpty()){
            List<Document> documentList = new ArrayList<>();
            for (QueryDocumentSnapshot queryDocumentSnapshot : document) {
                Document doc = queryDocumentSnapshot.toObject(Document.class);
                documentList.add(doc);
            }
            return documentList;
        }
        return null;
    }
}
