--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2021-11-09 01:11:21 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16427)
-- Name: actor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actor (
    actorid integer NOT NULL,
    actorname character varying(30)
);


ALTER TABLE public.actor OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16432)
-- Name: country; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.country (
    countryid integer NOT NULL,
    countryname character varying(30)
);


ALTER TABLE public.country OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16422)
-- Name: genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genre (
    genreid integer NOT NULL,
    genrename character varying(50)
);


ALTER TABLE public.genre OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16454)
-- Name: showactor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.showactor (
    showid integer NOT NULL,
    actorid integer NOT NULL
);


ALTER TABLE public.showactor OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16437)
-- Name: shows; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shows (
    showid integer NOT NULL,
    showname character varying(50),
    description character varying(1000),
    numberofreviews integer,
    averagerating integer,
    genreid integer,
    countryid integer,
    isdiscontinued "char"
);


ALTER TABLE public.shows OWNER TO postgres;

--
-- TOC entry 3599 (class 0 OID 16427)
-- Dependencies: 210
-- Data for Name: actor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actor (actorid, actorname) FROM stdin;
1	Jim Parsons
2	Kaley Cuoco
3	Grant Gustin
4	Cardice Patton
5	Finn Wolfhard
6	Winona Ryder
7	David Ramsey
8	Katie Cassidy
9	Aaron Paul
10	Anna Gunn
11	Justin Roiland
12	Spencer Grammer
13	Michel Gill
14	Kevin Spacey
15	Hugh Laurice
16	Omar Epps
17	Trey Parker
18	Matt Stone
19	Alex Bonstein
20	Seth Green
\.


--
-- TOC entry 3600 (class 0 OID 16432)
-- Dependencies: 211
-- Data for Name: country; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.country (countryid, countryname) FROM stdin;
1	US
\.


--
-- TOC entry 3598 (class 0 OID 16422)
-- Dependencies: 209
-- Data for Name: genre; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.genre (genreid, genrename) FROM stdin;
1	comedy
2	animated
3	superhero
4	science fiction
5	drama
6	crime
\.


--
-- TOC entry 3602 (class 0 OID 16454)
-- Dependencies: 213
-- Data for Name: showactor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.showactor (showid, actorid) FROM stdin;
1	1
1	2
2	3
2	4
3	5
3	6
4	7
4	8
5	9
5	10
6	11
6	12
7	13
7	14
8	15
8	16
9	17
9	18
10	19
10	20
\.


--
-- TOC entry 3601 (class 0 OID 16437)
-- Dependencies: 212
-- Data for Name: shows; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.shows (showid, showname, description, numberofreviews, averagerating, genreid, countryid, isdiscontinued) FROM stdin;
3	Stranger Things	When a young boy disappears, his mother, a police chief and his friends must confront terrifying supernatural forces in order to get him back.	4279	2	4	1	t
4	Arrow	Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.	414	3	3	1	t
5	Breaking Bad	A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family future.	70	3	6	1	t
7	House Of Cards	A Congressman works with his equally conniving wife to exact revenge on the people who betrayed him.	1	3	5	1	t
8	House M.D.	An antisocial maverick doctor who specializes in diagnostic medicine does whatever it takes to solve puzzling cases that come his way using his crack team of doctors and his wits.	1	4	5	1	t
1	The big bang theory	A woman who moves into an apartment across the hall from two brilliant but socially awkward physicist shows them how little they know about life outside of the laboratory.	158	3	1	1	t
2	The Flash	After being struck by lightning, Barry Allen weaks up from his coma to discover he has been given the power of super speed, becoming the next Flash, fighting crime in Central City.	100	3	3	1	f
6	Rick And Morty	An animated series that follows the exploits of a super scientist and his not-so-bright grandson.	6	4	2	1	f
9	South Park	Follows the misadventures of four irreverent grade-schoolers in the quiet, dysfunctional town of South Park, Colorado.	4	1	2	1	f
10	Family Guy	In a wacky Rhode Island town, a dysfunctional family strive to cope with everyday life as they are thrown from one crazy scenario to another.	6	3	2	1	f
\.


--
-- TOC entry 3448 (class 2606 OID 16431)
-- Name: actor actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actor
    ADD CONSTRAINT actor_pkey PRIMARY KEY (actorid);


--
-- TOC entry 3450 (class 2606 OID 16436)
-- Name: country country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.country
    ADD CONSTRAINT country_pkey PRIMARY KEY (countryid);


--
-- TOC entry 3446 (class 2606 OID 16426)
-- Name: genre genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (genreid);


--
-- TOC entry 3454 (class 2606 OID 16458)
-- Name: showactor showactor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.showactor
    ADD CONSTRAINT showactor_pkey PRIMARY KEY (showid, actorid);


--
-- TOC entry 3452 (class 2606 OID 16443)
-- Name: shows shows_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shows
    ADD CONSTRAINT shows_pkey PRIMARY KEY (showid);


--
-- TOC entry 3458 (class 2606 OID 16464)
-- Name: showactor showactor_actorid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.showactor
    ADD CONSTRAINT showactor_actorid_fkey FOREIGN KEY (actorid) REFERENCES public.actor(actorid);


--
-- TOC entry 3457 (class 2606 OID 16459)
-- Name: showactor showactor_showid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.showactor
    ADD CONSTRAINT showactor_showid_fkey FOREIGN KEY (showid) REFERENCES public.shows(showid);


--
-- TOC entry 3456 (class 2606 OID 16449)
-- Name: shows shows_countryid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shows
    ADD CONSTRAINT shows_countryid_fkey FOREIGN KEY (countryid) REFERENCES public.country(countryid);


--
-- TOC entry 3455 (class 2606 OID 16444)
-- Name: shows shows_genreid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shows
    ADD CONSTRAINT shows_genreid_fkey FOREIGN KEY (genreid) REFERENCES public.genre(genreid);


-- Completed on 2021-11-09 01:11:22 CET

--
-- PostgreSQL database dump complete
--

