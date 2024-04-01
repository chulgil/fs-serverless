package com.barodream.backend.service;


import com.barodream.backend.entity.PurchaseHistory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Service
@RequiredArgsConstructor
public class DDBService {

    private final DynamoDbEnhancedClient enhancedClient;

    @Value("${dynamodb.tableName.purchase}")
    private String tableName;

    public void putItem(PurchaseHistory purchaseHistory) {
        // 변환된 객체를 DynamoDB 테이블에 저장
        DynamoDbTable<PurchaseHistory> purchaseHistoryTable = enhancedClient.table(
            tableName, TableSchema.fromBean(PurchaseHistory.class)
        );
        purchaseHistoryTable.putItem(purchaseHistory);
    }

    public PurchaseHistory getItem(String userId, Long createdAt) {
        // DynamoDB 테이블에서 아이템을 조회
        DynamoDbTable<PurchaseHistory> purchaseHistoryTable = enhancedClient.table(
            tableName, TableSchema.fromBean(PurchaseHistory.class));
        return purchaseHistoryTable.getItem(
            r -> r.key(k -> k.partitionValue(userId).sortValue(createdAt)));
    }

    /**
     * 사용자 ID에 따라 구매 이력을 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 해당 사용자의 구매 이력 목록
     */
    public List<PurchaseHistory> getItems(String userId) {
        // DynamoDB 테이블 객체를 얻습니다.
        DynamoDbTable<PurchaseHistory> purchaseHistoryTable = enhancedClient.table(
            tableName, TableSchema.fromBean(PurchaseHistory.class));

        // 사용자 ID를 기반으로 Query 수행
        QueryConditional queryConditional = QueryConditional
            .keyEqualTo(k -> k.partitionValue(userId));

        // 구매 이력 목록 조회
        return purchaseHistoryTable.query(r -> r.queryConditional(queryConditional))
            .items()
            .stream()
            .collect(Collectors.toList());
    }

}
