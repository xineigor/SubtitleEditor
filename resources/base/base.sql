PGDMP  ,                    |            subtitle    16.3 (Debian 16.3-1.pgdg120+1)    16.3 1    Q           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            R           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            S           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            T           1262    16388    subtitle    DATABASE     s   CREATE DATABASE subtitle WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';
    DROP DATABASE subtitle;
                postgres    false            Q           1247    16390    position_type    TYPE     T   CREATE TYPE public.position_type AS ENUM (
    'top',
    'default',
    'other'
);
     DROP TYPE public.position_type;
       public          postgres    false            T           1247    16398    sub_type    TYPE     K   CREATE TYPE public.sub_type AS ENUM (
    'ass',
    'srt',
    'other'
);
    DROP TYPE public.sub_type;
       public          postgres    false            �            1259    16405    subtitle_block    TABLE     �   CREATE TABLE public.subtitle_block (
    id integer NOT NULL,
    sub_file_id integer NOT NULL,
    start_time time without time zone,
    end_time time without time zone
);
 "   DROP TABLE public.subtitle_block;
       public         heap    postgres    false            �            1259    16408    subtitle_block_id_seq    SEQUENCE     �   CREATE SEQUENCE public.subtitle_block_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.subtitle_block_id_seq;
       public          postgres    false    215            U           0    0    subtitle_block_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.subtitle_block_id_seq OWNED BY public.subtitle_block.id;
          public          postgres    false    216            �            1259    16409    subtitle_file    TABLE       CREATE TABLE public.subtitle_file (
    id integer NOT NULL,
    file_name character varying(128) NOT NULL,
    format public.sub_type NOT NULL,
    language character varying(64) NOT NULL,
    movie_title character varying(64),
    upload_date timestamp with time zone
);
 !   DROP TABLE public.subtitle_file;
       public         heap    postgres    false    852            �            1259    16412    subtitle_file_id_seq    SEQUENCE     �   CREATE SEQUENCE public.subtitle_file_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.subtitle_file_id_seq;
       public          postgres    false    217            V           0    0    subtitle_file_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.subtitle_file_id_seq OWNED BY public.subtitle_file.id;
          public          postgres    false    218            �            1259    16413    subtitle_format_specific    TABLE     �   CREATE TABLE public.subtitle_format_specific (
    id integer NOT NULL,
    sub_file_id integer NOT NULL,
    format_specific_data text
);
 ,   DROP TABLE public.subtitle_format_specific;
       public         heap    postgres    false            �            1259    16418    subtitle_format_specific_id_seq    SEQUENCE     �   CREATE SEQUENCE public.subtitle_format_specific_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.subtitle_format_specific_id_seq;
       public          postgres    false    219            W           0    0    subtitle_format_specific_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.subtitle_format_specific_id_seq OWNED BY public.subtitle_format_specific.id;
          public          postgres    false    220            �            1259    16419    subtitle_line    TABLE     �   CREATE TABLE public.subtitle_line (
    id integer NOT NULL,
    sub_block_id integer NOT NULL,
    sub_text text,
    line_number integer,
    "position" public.position_type
);
 !   DROP TABLE public.subtitle_line;
       public         heap    postgres    false    849            �            1259    16424    subtitle_line_id_seq    SEQUENCE     �   CREATE SEQUENCE public.subtitle_line_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.subtitle_line_id_seq;
       public          postgres    false    221            X           0    0    subtitle_line_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.subtitle_line_id_seq OWNED BY public.subtitle_line.id;
          public          postgres    false    222            �            1259    16425    subtitle_style    TABLE     w   CREATE TABLE public.subtitle_style (
    id integer NOT NULL,
    sub_file_id integer NOT NULL,
    style_data text
);
 "   DROP TABLE public.subtitle_style;
       public         heap    postgres    false            �            1259    16430    subtitle_style_id_seq    SEQUENCE     �   CREATE SEQUENCE public.subtitle_style_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.subtitle_style_id_seq;
       public          postgres    false    223            Y           0    0    subtitle_style_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.subtitle_style_id_seq OWNED BY public.subtitle_style.id;
          public          postgres    false    224            �           2604    16431    subtitle_block id    DEFAULT     v   ALTER TABLE ONLY public.subtitle_block ALTER COLUMN id SET DEFAULT nextval('public.subtitle_block_id_seq'::regclass);
 @   ALTER TABLE public.subtitle_block ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215            �           2604    16432    subtitle_file id    DEFAULT     t   ALTER TABLE ONLY public.subtitle_file ALTER COLUMN id SET DEFAULT nextval('public.subtitle_file_id_seq'::regclass);
 ?   ALTER TABLE public.subtitle_file ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217            �           2604    16433    subtitle_format_specific id    DEFAULT     �   ALTER TABLE ONLY public.subtitle_format_specific ALTER COLUMN id SET DEFAULT nextval('public.subtitle_format_specific_id_seq'::regclass);
 J   ALTER TABLE public.subtitle_format_specific ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219            �           2604    16434    subtitle_line id    DEFAULT     t   ALTER TABLE ONLY public.subtitle_line ALTER COLUMN id SET DEFAULT nextval('public.subtitle_line_id_seq'::regclass);
 ?   ALTER TABLE public.subtitle_line ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    221            �           2604    16435    subtitle_style id    DEFAULT     v   ALTER TABLE ONLY public.subtitle_style ALTER COLUMN id SET DEFAULT nextval('public.subtitle_style_id_seq'::regclass);
 @   ALTER TABLE public.subtitle_style ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    223            E          0    16405    subtitle_block 
   TABLE DATA           O   COPY public.subtitle_block (id, sub_file_id, start_time, end_time) FROM stdin;
    public          postgres    false    215   �9       G          0    16409    subtitle_file 
   TABLE DATA           b   COPY public.subtitle_file (id, file_name, format, language, movie_title, upload_date) FROM stdin;
    public          postgres    false    217   :       I          0    16413    subtitle_format_specific 
   TABLE DATA           Y   COPY public.subtitle_format_specific (id, sub_file_id, format_specific_data) FROM stdin;
    public          postgres    false    219   �:       K          0    16419    subtitle_line 
   TABLE DATA           \   COPY public.subtitle_line (id, sub_block_id, sub_text, line_number, "position") FROM stdin;
    public          postgres    false    221   �:       M          0    16425    subtitle_style 
   TABLE DATA           E   COPY public.subtitle_style (id, sub_file_id, style_data) FROM stdin;
    public          postgres    false    223   �;       Z           0    0    subtitle_block_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.subtitle_block_id_seq', 7, true);
          public          postgres    false    216            [           0    0    subtitle_file_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.subtitle_file_id_seq', 22, true);
          public          postgres    false    218            \           0    0    subtitle_format_specific_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.subtitle_format_specific_id_seq', 1, false);
          public          postgres    false    220            ]           0    0    subtitle_line_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.subtitle_line_id_seq', 7, true);
          public          postgres    false    222            ^           0    0    subtitle_style_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.subtitle_style_id_seq', 1, true);
          public          postgres    false    224            �           2606    16437 "   subtitle_block subtitle_block_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.subtitle_block
    ADD CONSTRAINT subtitle_block_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.subtitle_block DROP CONSTRAINT subtitle_block_pkey;
       public            postgres    false    215            �           2606    16439     subtitle_file subtitle_file_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.subtitle_file
    ADD CONSTRAINT subtitle_file_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.subtitle_file DROP CONSTRAINT subtitle_file_pkey;
       public            postgres    false    217            �           2606    16441 6   subtitle_format_specific subtitle_format_specific_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY public.subtitle_format_specific
    ADD CONSTRAINT subtitle_format_specific_pkey PRIMARY KEY (id);
 `   ALTER TABLE ONLY public.subtitle_format_specific DROP CONSTRAINT subtitle_format_specific_pkey;
       public            postgres    false    219            �           2606    16443     subtitle_line subtitle_line_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.subtitle_line
    ADD CONSTRAINT subtitle_line_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.subtitle_line DROP CONSTRAINT subtitle_line_pkey;
       public            postgres    false    221            �           2606    16445 "   subtitle_style subtitle_style_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.subtitle_style
    ADD CONSTRAINT subtitle_style_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.subtitle_style DROP CONSTRAINT subtitle_style_pkey;
       public            postgres    false    223            �           2606    16447 0   subtitle_style subtitle_style_unique_sub_file_id 
   CONSTRAINT     r   ALTER TABLE ONLY public.subtitle_style
    ADD CONSTRAINT subtitle_style_unique_sub_file_id UNIQUE (sub_file_id);
 Z   ALTER TABLE ONLY public.subtitle_style DROP CONSTRAINT subtitle_style_unique_sub_file_id;
       public            postgres    false    223            �           2606    16449 +   subtitle_format_specific unique_sub_file_id 
   CONSTRAINT     m   ALTER TABLE ONLY public.subtitle_format_specific
    ADD CONSTRAINT unique_sub_file_id UNIQUE (sub_file_id);
 U   ALTER TABLE ONLY public.subtitle_format_specific DROP CONSTRAINT unique_sub_file_id;
       public            postgres    false    219            �           1259    16450    fki_fk_subtitle_block    INDEX     W   CREATE INDEX fki_fk_subtitle_block ON public.subtitle_line USING btree (sub_block_id);
 )   DROP INDEX public.fki_fk_subtitle_block;
       public            postgres    false    221            �           1259    16451    fki_fk_subtitle_file    INDEX     `   CREATE INDEX fki_fk_subtitle_file ON public.subtitle_format_specific USING btree (sub_file_id);
 (   DROP INDEX public.fki_fk_subtitle_file;
       public            postgres    false    219            �           2606    16452    subtitle_line fk_subtitle_block    FK CONSTRAINT     �   ALTER TABLE ONLY public.subtitle_line
    ADD CONSTRAINT fk_subtitle_block FOREIGN KEY (sub_block_id) REFERENCES public.subtitle_block(id) NOT VALID;
 I   ALTER TABLE ONLY public.subtitle_line DROP CONSTRAINT fk_subtitle_block;
       public          postgres    false    215    3235    221            �           2606    16457    subtitle_block fk_subtitle_file    FK CONSTRAINT     �   ALTER TABLE ONLY public.subtitle_block
    ADD CONSTRAINT fk_subtitle_file FOREIGN KEY (sub_file_id) REFERENCES public.subtitle_file(id) NOT VALID;
 I   ALTER TABLE ONLY public.subtitle_block DROP CONSTRAINT fk_subtitle_file;
       public          postgres    false    215    3237    217            �           2606    16462 )   subtitle_format_specific fk_subtitle_file    FK CONSTRAINT     �   ALTER TABLE ONLY public.subtitle_format_specific
    ADD CONSTRAINT fk_subtitle_file FOREIGN KEY (sub_file_id) REFERENCES public.subtitle_file(id) NOT VALID;
 S   ALTER TABLE ONLY public.subtitle_format_specific DROP CONSTRAINT fk_subtitle_file;
       public          postgres    false    219    217    3237            �           2606    16467    subtitle_style fk_subtitle_file    FK CONSTRAINT     �   ALTER TABLE ONLY public.subtitle_style
    ADD CONSTRAINT fk_subtitle_file FOREIGN KEY (sub_file_id) REFERENCES public.subtitle_file(id) NOT VALID;
 I   ALTER TABLE ONLY public.subtitle_style DROP CONSTRAINT fk_subtitle_file;
       public          postgres    false    217    223    3237            E   G   x�3�4�40�!#=3c�D�̜�!���fz��\�9ƶг0�2�4�i�)�1N3�rf��qqq Q�P      G   |   x�3���K��LMN�700�K,.���<�����������������ip��-.*�ab���-*�[]TJX�	�v���h7E�nD����fhz	�I�9�^b|���S;A_#i����� ���      I      x������ � �      K     x�U�=N�@���)� +K��#P �����J^ǖB�]�&T��J028Z�0{#f\�fwv�|o�L`wF�]�0��F�ݚ2�cR��ǵ��0�+�N�֮L��������
m�R/�R;S�4�h�t�1)�'S>���.*�ڒs]�����<�9�y:Q�5�+{��Q�'ע����X �3ݓg��7�_��Em�����NZ��:~4C�q��%\ c~H�щ�r��cyq	/� ��뚭بe��:	��U�D=ˠq�$����d      M      x�3���N.�,(Q��Kˏ����� L�     