package com.shinjimjim.bbs.repository;
// Spring Data JPA を使って「データベースから投稿（Post）を取得するリポジトリ（Repository）」を定義している
import com.shinjimjim.bbs.model.Post;
import com.shinjimjim.bbs.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> { //interface（インターフェース）で定義されており、Springが自動的に実装を作ってくれるリポジトリです。
    //JpaRepository<Post, Long> を継承しているので、Postエンティティに対してsave(), findById(), findAll(), delete() などの基本的な操作が 自動で使える。Long：主キー（id）の型
    List<Post> findByThreadOrderByPostedAtAsc(Thread thread); //「あるスレッドに属する投稿を、投稿日時（postedAt）の昇順で並べて取得する」メソッドです。
    //findByThread：threadというフィールドに一致するデータを検索。OrderByPostedAtAsc：postedAtというフィールドで昇順（Asc）に並べる。
    //Spring Data JPA はこの命名規則に従うことで SQLを書かなくても自動的に処理を生成 してくれます。
}
