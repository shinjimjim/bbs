package com.shinjimjim.bbs.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
//エンティティ（Entity）クラスでリレーションを構築し、投稿（Post）をスレッド（Thread）に紐づける
@Entity //このクラスが データベースのテーブル として使われることを意味します。Post クラスは、Spring Data JPA により自動的に post という名前のテーブルにマッピングされます。
public class Post {

    @Id //@Id：主キー（Primary Key）を示します。
    @GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue：自動採番（DB側でIDを自動生成）。GenerationType.IDENTITY：MySQLやPostgreSQLなどの AUTO_INCREMENT 相当の仕組み。
    private Long id;

    @Column(length = 1000) //この content カラムの長さを最大1000文字に制限。
    private String content;

    private String username; //アノテーションを省略していますが、自動でカラムとして登録されます。

    private LocalDateTime postedAt; //アノテーションを省略していますが、自動でカラムとして登録されます。

    @ManyToOne //「多対一」の関係を示します。多くの投稿（Post）は、1つのスレッド（Thread）に属する という関係。
    @JoinColumn(name = "thread_id") //外部キー（FK）に相当します。post テーブルに thread_id カラムを作り、thread テーブルと接続します。
    //つまりpost.thread_id → thread.id（外部キーで関連づけ）
    private Thread thread;

    //コンストラクタでの自動時間設定
    public Post() {
        this.postedAt = LocalDateTime.now(); //LocalDateTime.now() は現在の日時を返すメソッド。
    }

    //引数つきコンストラクタ
    public Post(String content, String username, Thread thread) { //new Post("こんにちは", "名無し", threadObj); のようにインスタンス生成時に値を入れるためのもの。
        this.content = content;
        this.username = username;
        this.thread = thread; //this.xxx = xxx は、引数をフィールドに代入しています。
        this.postedAt = LocalDateTime.now(); //投稿時刻を自動でセット（newされた時点の時間）。
    }

    // getter/setter省略（必要なら追加）
    //Spring Boot のフォームバインディング（@ModelAttribute）でも getter/setter が必要です。
    //Spring Boot（Spring Data JPA）が使うのは「引数なしコンストラクタ + setter」だけでOK。だからカスタムコンストラクタやgetter/setterは「開発者の利便性のため」に追加されます
    public Long getId() { return id; }
    public String getContent() { return content; } //getContent()：投稿内容を取得する
    public void setContent(String content) { this.content = content; } //setContent("修正")：投稿内容を更新する
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public LocalDateTime getPostedAt() { return postedAt; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }
    public Thread getThread() { return thread; }
    public void setThread(Thread thread) { this.thread = thread; }
}
