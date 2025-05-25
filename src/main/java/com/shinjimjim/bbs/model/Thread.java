package com.shinjimjim.bbs.model;
//Spring Boot + JPAでは、アプリで扱うデータ（スレッドや投稿など）を「エンティティクラス」というJavaのクラスで表現します。
//これにより、SQLを書かなくてもDBとやり取りできるようになります。
import jakarta.persistence.*; //PA（Java Persistence API） という仕様で定義されたクラスやアノテーションを使うためのインポート
import java.time.LocalDateTime; //Java 標準APIの java.time パッケージから、日時を表すクラス LocalDateTime をインポート

@Entity //このクラスが データベース上のテーブルと対応していることを示します。Thread クラス → thread テーブルに自動対応されます（テーブル名の指定も可能）。
public class Thread { //スレッド（Thread）をデータとして扱うためのクラス

    @Id //このフィールドが「主キー（Primary Key）」であることを示します。
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自動採番（DBが自動でIDをつけてくれる）を意味します。MySQLでよく使う「AUTO_INCREMENT」のような動作。
    private Long id;

    private String title; //title: スレッドのタイトル（ユーザーが入力）

    private LocalDateTime createdAt; //createdAt: スレッドが作られた日時（現在日時を自動で入れる）

    // コンストラクタ　引数なしのコンストラクタはJPAが必要とするため必須です。
    public Thread() {
        this.createdAt = LocalDateTime.now(); //LocalDateTime.now() を使って、作成日時を自動設定します。
    }

    public Thread(String title) {
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    // getter/setter（省略可：Lombok使えば短くできる）Spring MVCやThymeleafがフォームのデータを扱うために必要です。@ModelAttribute や th:field などで使われます。
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
