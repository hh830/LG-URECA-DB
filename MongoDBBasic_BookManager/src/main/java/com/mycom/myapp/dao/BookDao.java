package com.mycom.myapp.dao;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycom.myapp.dto.BookDto;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

// jdbc처럼 연결해줘야 함
public class BookDao {

    static final String CONNECTION_STRING = "mongodb://localhost:27017"; // db는 따로 씀
    static final String DATABASE_NAME = "madangdb"; // db 이름
    static final String COLLECTION_NAME = "book"; // 테이블 이름

    // username, password 필요 x

    private MongoClient mongoClient; // jdbc Connection 유사
    private MongoDatabase database;
    private MongoCollection<Document> collection; // import org.bson.Document

    // 생성자 mongodb 연결 초기화
    public BookDao() {
        try{
            this.mongoClient = MongoClients.create(CONNECTION_STRING); // Client's'임. s 있음
            this.database = this.mongoClient.getDatabase(DATABASE_NAME);
            this.collection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<BookDto> listBook(){
        List<BookDto> bookList = new ArrayList<>();

        // collection.find() : 반환 값 형변환 해야 함. iterable하므로 for each 가능
        for(Document doc : collection.find()){
            BookDto bookDto = new BookDto();
            bookDto.setBookId(doc.getInteger("bookid")); // document에서 bookid 필드를 가져옴
            bookDto.setBookName(doc.getString("bookname"));
            bookDto.setPublisher(doc.getString("publisher"));
            bookDto.setPrice(doc.getInteger("price"));

            bookList.add(bookDto);
        }

        return bookList;
    }

    public List<BookDto> listBookSearch(String searchWord){
        List<BookDto> bookList = new ArrayList<>();

        Document filterDocument = new Document("$regex", searchWord).append("$options", "i");
        Document bookNameFilter = new Document("bookname", filterDocument);

        for(Document doc : collection.find(bookNameFilter)){
            BookDto bookDto = new BookDto();
            bookDto.setBookId(doc.getInteger("bookid")); // document에서 bookid 필드를 가져옴
            bookDto.setBookName(doc.getString("bookname"));
            bookDto.setPublisher(doc.getString("publisher"));
            bookDto.setPrice(doc.getInteger("price"));

            bookList.add(bookDto);
        }

        return bookList;
    }

    public BookDto detailBook(int bookid) {
        Document doc = collection.find( eq("bookid", bookid )).first();
        BookDto bookDto = null;

        if( doc != null ){
            bookDto = new BookDto();
            bookDto.setBookId(doc.getInteger("bookid")); // document에서 bookid 필드를 가져옴
            bookDto.setBookName(doc.getString("bookname"));
            bookDto.setPublisher(doc.getString("publisher"));
            bookDto.setPrice(doc.getInteger("price"));

        }
        return bookDto;
    }

    public void insertBook(BookDto bookDto) {
        Document document = new Document("bookid", bookDto.getBookId())
                .append("bookname", bookDto.getBookName())
                .append("publisher", bookDto.getPublisher())
                .append("price", bookDto.getPrice());
        collection.insertOne(document);
    }

    public void updateBook(BookDto bookDto) {
        Bson filter = eq("bookid", bookDto.getBookId());
        Bson update = combine(
                set("bookname", bookDto.getBookName()),
                set("publisher", bookDto.getPublisher()),
                set("price", bookDto.getPrice())
        );
        collection.updateOne(filter, update);
    }

    public void deleteBook(int bookid) {
        collection.deleteOne(eq("bookid", bookid));
    }
}