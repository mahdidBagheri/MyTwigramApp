PGDMP         
                y           TwigramServer01    13.2    13.2     ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    92285    TwigramServer01    DATABASE     u   CREATE DATABASE "TwigramServer01" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
 !   DROP DATABASE "TwigramServer01";
                postgres    false                        3079    92286 	   uuid-ossp 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;
    DROP EXTENSION "uuid-ossp";
                   false            ?           0    0    EXTENSION "uuid-ossp"    COMMENT     W   COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';
                        false    2            ?            1259    92297 	   ChatTable    TABLE     ?   CREATE TABLE public."ChatTable" (
    "UUID" uuid NOT NULL,
    "User1UUID" uuid NOT NULL,
    "User2UUID" uuid NOT NULL,
    "DateStarted" date NOT NULL,
    "PVTableName" character varying(100) NOT NULL
);
    DROP TABLE public."ChatTable";
       public         heap    postgres    false            ?            1259    92300    LogTable    TABLE     ?   CREATE TABLE public."LogTable" (
    "ID" bigint NOT NULL,
    "Level" character varying(50) NOT NULL,
    "Content" text NOT NULL,
    "Date" timestamp without time zone
);
    DROP TABLE public."LogTable";
       public         heap    postgres    false            ?            1259    92306    LogTable_ID_seq    SEQUENCE     z   CREATE SEQUENCE public."LogTable_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."LogTable_ID_seq";
       public          postgres    false    202            ?           0    0    LogTable_ID_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public."LogTable_ID_seq" OWNED BY public."LogTable"."ID";
          public          postgres    false    203            ?            1259    92308    ReportTable    TABLE     d   CREATE TABLE public."ReportTable" (
    "TwittUUID" uuid NOT NULL,
    "NumberOfReports" integer
);
 !   DROP TABLE public."ReportTable";
       public         heap    postgres    false            ?            1259    92311    TwittsTable    TABLE       CREATE TABLE public."TwittsTable" (
    "TwittUUID" uuid NOT NULL,
    text text NOT NULL,
    "Author" character varying(50) NOT NULL,
    "Likes" character varying(50),
    "Retwitts" character varying(50),
    "Replies" character varying(50),
    "Reports" character varying(50),
    "Date" timestamp without time zone,
    "ParentReply" uuid,
    "ParentQuote" uuid,
    "QuoteTwitts" character varying(100),
    "ParentRetwitt" uuid,
    "Type" character varying(20),
    "hasPic" boolean,
    "PicAddress" character varying(200)
);
 !   DROP TABLE public."TwittsTable";
       public         heap    postgres    false            ?            1259    92317 
   UsersTable    TABLE     ?  CREATE TABLE public."UsersTable" (
    "UserUUID" uuid NOT NULL,
    "UserName" character varying(50) NOT NULL,
    "Pass" character varying(50) NOT NULL,
    "Fname" character varying(50) NOT NULL,
    "Lname" character varying(50) NOT NULL,
    "Email" character varying(100) NOT NULL,
    "Followers" character varying(50),
    "Following" character varying(50),
    "BlackList" character varying(50),
    "Privacy" character varying(10),
    "BirthDate" date,
    "PhoneNumber" character varying(20),
    "Bio" character varying(300),
    "Status" character varying(50),
    "LastSeen" timestamp without time zone,
    "PendingFollowingRequest" character varying(100),
    "PendingFollowersRequest" character varying(100),
    "NewFollowers" character varying(100),
    "NewUnFollowers" character varying(100),
    "MutedUsers" character varying(100),
    "Twitts" character varying(50),
    "Likes" character varying(50),
    "SavedTwitts" character varying(100),
    "ReTwitts" character varying(50),
    "Chats" character varying(50),
    "Replies" character varying(50),
    "Spams" character varying(50),
    "Spamers" character varying(50),
    "DateJoined" timestamp without time zone NOT NULL,
    "Qoutes" character varying(100),
    "Activities" character varying(100),
    "LastSeenMode" character varying(50),
    "Notifications" character varying(100),
    "Groups" character varying(50),
    "ProfilePic" character varying(200),
    "Session" character varying(50)
);
     DROP TABLE public."UsersTable";
       public         heap    postgres    false            @           2604    92323    LogTable ID    DEFAULT     p   ALTER TABLE ONLY public."LogTable" ALTER COLUMN "ID" SET DEFAULT nextval('public."LogTable_ID_seq"'::regclass);
 >   ALTER TABLE public."LogTable" ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    203    202            ?          0    92297 	   ChatTable 
   TABLE DATA           e   COPY public."ChatTable" ("UUID", "User1UUID", "User2UUID", "DateStarted", "PVTableName") FROM stdin;
    public          postgres    false    201   #       ?          0    92300    LogTable 
   TABLE DATA           F   COPY public."LogTable" ("ID", "Level", "Content", "Date") FROM stdin;
    public          postgres    false    202   1#       ?          0    92308    ReportTable 
   TABLE DATA           G   COPY public."ReportTable" ("TwittUUID", "NumberOfReports") FROM stdin;
    public          postgres    false    204   N#       ?          0    92311    TwittsTable 
   TABLE DATA           ?   COPY public."TwittsTable" ("TwittUUID", text, "Author", "Likes", "Retwitts", "Replies", "Reports", "Date", "ParentReply", "ParentQuote", "QuoteTwitts", "ParentRetwitt", "Type", "hasPic", "PicAddress") FROM stdin;
    public          postgres    false    205   k#       ?          0    92317 
   UsersTable 
   TABLE DATA           ?  COPY public."UsersTable" ("UserUUID", "UserName", "Pass", "Fname", "Lname", "Email", "Followers", "Following", "BlackList", "Privacy", "BirthDate", "PhoneNumber", "Bio", "Status", "LastSeen", "PendingFollowingRequest", "PendingFollowersRequest", "NewFollowers", "NewUnFollowers", "MutedUsers", "Twitts", "Likes", "SavedTwitts", "ReTwitts", "Chats", "Replies", "Spams", "Spamers", "DateJoined", "Qoutes", "Activities", "LastSeenMode", "Notifications", "Groups", "ProfilePic", "Session") FROM stdin;
    public          postgres    false    206   ?#       ?           0    0    LogTable_ID_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."LogTable_ID_seq"', 86, true);
          public          postgres    false    203            B           2606    92325    ChatTable ChatTable_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public."ChatTable"
    ADD CONSTRAINT "ChatTable_pkey" PRIMARY KEY ("UUID");
 F   ALTER TABLE ONLY public."ChatTable" DROP CONSTRAINT "ChatTable_pkey";
       public            postgres    false    201            D           2606    92327    LogTable LogTable_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public."LogTable"
    ADD CONSTRAINT "LogTable_pkey" PRIMARY KEY ("ID");
 D   ALTER TABLE ONLY public."LogTable" DROP CONSTRAINT "LogTable_pkey";
       public            postgres    false    202            F           2606    92329    ReportTable SpamTable_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public."ReportTable"
    ADD CONSTRAINT "SpamTable_pkey" PRIMARY KEY ("TwittUUID");
 H   ALTER TABLE ONLY public."ReportTable" DROP CONSTRAINT "SpamTable_pkey";
       public            postgres    false    204            H           2606    92331    TwittsTable Twitts_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public."TwittsTable"
    ADD CONSTRAINT "Twitts_pkey" PRIMARY KEY ("TwittUUID");
 E   ALTER TABLE ONLY public."TwittsTable" DROP CONSTRAINT "Twitts_pkey";
       public            postgres    false    205            J           2606    92333    UsersTable UsersTable_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public."UsersTable"
    ADD CONSTRAINT "UsersTable_pkey" PRIMARY KEY ("UserUUID");
 H   ALTER TABLE ONLY public."UsersTable" DROP CONSTRAINT "UsersTable_pkey";
       public            postgres    false    206            ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?     