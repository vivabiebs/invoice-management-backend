package invoicemanagementbackend.service;

import invoicemanagementbackend.model.Payer;
import invoicemanagementbackend.model.Relationship;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class TestService {
    public String getPayerIDByRelationshipId(String id) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection("relationships").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if(document.exists()){
            Relationship relationship;
            relationship = document.toObject(Relationship.class);
            assert relationship != null;
            return relationship.getPayerID();
        }
        return null;
    }

    public Payer getPayer(String payerId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection("payers").document(payerId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if(document.exists()){
            Payer payer;
            payer = document.toObject(Payer.class);
            payer.setId(document.getId());
            log.info("document: {}",document.getId());
            assert payer != null;
            return payer;
        }
        return null;
    }
}
