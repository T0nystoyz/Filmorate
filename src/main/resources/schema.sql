CREATE TABLE IF NOT EXISTS "rating" (
    "rating_id" integer   NOT NULL,
    "name" varchar(10)   NOT NULL,
    CONSTRAINT "pk_rating" PRIMARY KEY ("rating_id"),
    CONSTRAINT "uc_rating_name" UNIQUE ("name")
);

CREATE TABLE IF NOT EXISTS "genre" (
    "genre_id" integer   NOT NULL,
    "name" varchar(30)   NOT NULL,
    CONSTRAINT "pk_genre" PRIMARY KEY ("genre_id"),
    CONSTRAINT "uc_genre_name" UNIQUE ("name")
);

CREATE TABLE IF NOT EXISTS "film" (
    "film_id" integer   NOT NULL,
    "name" varchar(50)   NOT NULL,
    "description" varchar(200)   NOT NULL,
    "release_date" date   NOT NULL,
    "duration" integer   NOT NULL,
    "rating_id" integer   NOT NULL,
    CONSTRAINT "pk_film" PRIMARY KEY ("film_id"),
	CONSTRAINT "fk_film_rating_id" FOREIGN KEY("rating_id") REFERENCES "rating" ("rating_id")
);

CREATE TABLE IF NOT EXISTS "user" (
    "user_id" integer   NOT NULL,
    "email" varchar(50)   NOT NULL,
    "login" varchar(50)   NOT NULL,
    "name" varchar(50)   NOT NULL,
    "birthday" date   NOT NULL,
    CONSTRAINT "pk_user" PRIMARY KEY ("user_id"),
    CONSTRAINT "uc_user_email" UNIQUE ("email"),
    CONSTRAINT "uc_user_login" UNIQUE ("login")
);

CREATE TABLE IF NOT EXISTS "film_like" (
    "film_id" integer   NOT NULL,
    "user_id" integer   NOT NULL,
    CONSTRAINT "pk_film_like" PRIMARY KEY ("film_id","user_id"),
	CONSTRAINT "fk_film_like_film_id" FOREIGN KEY("film_id") REFERENCES "film" ("film_id"),
	CONSTRAINT "fk_film_like_user_id" FOREIGN KEY("user_id") REFERENCES "user" ("user_id")
);

CREATE TABLE IF NOT EXISTS "friendship" (
    "user_id1" integer   NOT NULL,
    "user_id2" integer   NOT NULL,
    "confirmed" boolean   NOT NULL,
    CONSTRAINT "pk_friendship" PRIMARY KEY (
        "user_id1","user_id2"
     ),
	 CONSTRAINT "fk_friendship_user_id1" FOREIGN KEY("user_id1") REFERENCES "user" ("user_id"),
	 CONSTRAINT "fk_friendship_user_id2" FOREIGN KEY("user_id2") REFERENCES "user" ("user_id")
);

CREATE TABLE IF NOT EXISTS "film_genre" (
    "film_id" integer   NOT NULL,
    "genre_id" integer   NOT NULL,
    CONSTRAINT "pk_film_genre" PRIMARY KEY ("film_id","genre_id"),
	CONSTRAINT "fk_film_genre_film_id" FOREIGN KEY("film_id") REFERENCES "film" ("film_id"),
	CONSTRAINT "fk_film_genre_genre_id" FOREIGN KEY("genre_id") REFERENCES "genre" ("genre_id")
);