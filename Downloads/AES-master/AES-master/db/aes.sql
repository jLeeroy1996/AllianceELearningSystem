-- phpMyAdmin SQL Dump
-- version 4.5.0.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 28, 2015 at 08:52 AM
-- Server version: 10.0.17-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aes`
--

-- --------------------------------------------------------

--
-- Table structure for table `business_unit`
--

CREATE TABLE `business_unit` (
  `id` int(11) NOT NULL,
  `business_unit` varchar(45) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(15) NOT NULL,
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edited_by` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `business_unit`
--

INSERT INTO `business_unit` (`id`, `business_unit`, `date_created`, `created_by`, `date_edited`, `edited_by`) VALUES
(1, 'Japan', '2015-09-04 12:28:41', '2cababa', '2015-11-28 07:12:38', 'migz123'),
(2, 'USA', '2015-08-31 12:21:50', '2cababa', '2015-11-28 00:41:50', 'bryan'),
(3, 'Philippines', '2015-09-12 11:40:11', '2cababa', '2015-11-28 00:41:52', 'bryan');

-- --------------------------------------------------------

--
-- Table structure for table `chapter`
--

CREATE TABLE `chapter` (
  `id` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `number` int(11) NOT NULL,
  `content` longtext NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(15) NOT NULL,
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edited_by` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `course_title` varchar(45) NOT NULL,
  `description` longtext NOT NULL,
  `trainer` varchar(15) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(15) NOT NULL,
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edited_by` varchar(15) NOT NULL,
  `course_category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `courses_taken`
--

CREATE TABLE `courses_taken` (
  `id` int(11) NOT NULL,
  `user` varchar(15) NOT NULL,
  `course` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(15) NOT NULL,
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edited_by` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `course_category`
--

CREATE TABLE `course_category` (
  `id` int(11) NOT NULL,
  `category` varchar(50) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(15) NOT NULL,
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edited_by` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE `exam` (
  `id` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  `title` varchar(45) NOT NULL,
  `description` longtext NOT NULL,
  `exam` longtext NOT NULL,
  `passing_percentage` int(11) NOT NULL DEFAULT '50',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(15) NOT NULL,
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edited_by` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `exam_scores`
--

CREATE TABLE `exam_scores` (
  `id` int(11) NOT NULL,
  `score` float NOT NULL,
  `max_score` float NOT NULL,
  `user` varchar(15) NOT NULL,
  `exam` int(11) NOT NULL,
  `passing_percentage` int(11) NOT NULL DEFAULT '50',
  `date_taken` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `times_taken` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `presentation`
--

CREATE TABLE `presentation` (
  `id` int(11) NOT NULL,
  `chapter` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `location` longtext NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_name` varchar(15) NOT NULL,
  `password` varchar(45) NOT NULL DEFAULT 'alliance@123',
  `name` varchar(45) NOT NULL,
  `position` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `created_by` varchar(15) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `edited_by` varchar(15) NOT NULL,
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `user_type_id` int(11) NOT NULL,
  `business_unit_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_name`, `password`, `name`, `position`, `email`, `created_by`, `date_created`, `edited_by`, `date_edited`, `active`, `user_type_id`, `business_unit_id`) VALUES
('admin', 'e36184fb30b6531feb7075ab8fe83883', 'admin', 'E-Learning Administrator', 'admin@admin', 'admin', '2015-11-28 07:42:41', 'admin', '2015-11-28 07:43:46', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_type`
--

CREATE TABLE `user_type` (
  `id` int(11) NOT NULL,
  `user_type` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_type`
--

INSERT INTO `user_type` (`id`, `user_type`) VALUES
(1, 'admin'),
(3, 'trainee'),
(2, 'trainer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `business_unit`
--
ALTER TABLE `business_unit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `business_unit_UNIQUE` (`business_unit`),
  ADD UNIQUE KEY `UK_2pq1aie1g5ti2nrf71e9d4ji8` (`business_unit`);

--
-- Indexes for table `chapter`
--
ALTER TABLE `chapter`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_chapter_course1_idx` (`course`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_course_user1_idx` (`trainer`),
  ADD KEY `fk_course_course_category1_idx` (`course_category_id`);

--
-- Indexes for table `courses_taken`
--
ALTER TABLE `courses_taken`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_courses_taken_user1_idx` (`user`),
  ADD KEY `fk_courses_taken_course1_idx` (`course`);

--
-- Indexes for table `course_category`
--
ALTER TABLE `course_category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `category_UNIQUE` (`category`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `UK_57w8kpu4p1b66igmtr8rwntpg` (`category`);

--
-- Indexes for table `exam`
--
ALTER TABLE `exam`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_exam_course1_idx` (`course`);

--
-- Indexes for table `exam_scores`
--
ALTER TABLE `exam_scores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_exam_scores_user1_idx` (`user`),
  ADD KEY `fk_exam_scores_exam1_idx` (`exam`);

--
-- Indexes for table `presentation`
--
ALTER TABLE `presentation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_presentation_chapter1_idx` (`chapter`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_name`),
  ADD KEY `fk_user_user_type1_idx` (`user_type_id`),
  ADD KEY `fk_user_business_unit1_idx` (`business_unit_id`);

--
-- Indexes for table `user_type`
--
ALTER TABLE `user_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_type_UNIQUE` (`user_type`),
  ADD UNIQUE KEY `UK_5httpn9ro2gg9yrqmd0flw1bo` (`user_type`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `business_unit`
--
ALTER TABLE `business_unit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `chapter`
--
ALTER TABLE `chapter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `courses_taken`
--
ALTER TABLE `courses_taken`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT for table `course_category`
--
ALTER TABLE `course_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `exam`
--
ALTER TABLE `exam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `exam_scores`
--
ALTER TABLE `exam_scores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `presentation`
--
ALTER TABLE `presentation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;
--
-- AUTO_INCREMENT for table `user_type`
--
ALTER TABLE `user_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `chapter`
--
ALTER TABLE `chapter`
  ADD CONSTRAINT `fk_chapter_course1` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `fk_course_course_category1` FOREIGN KEY (`course_category_id`) REFERENCES `course_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_course_user1` FOREIGN KEY (`trainer`) REFERENCES `user` (`user_name`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `courses_taken`
--
ALTER TABLE `courses_taken`
  ADD CONSTRAINT `fk_courses_taken_course1` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_courses_taken_user1` FOREIGN KEY (`user`) REFERENCES `user` (`user_name`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `fk_exam_course1` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `exam_scores`
--
ALTER TABLE `exam_scores`
  ADD CONSTRAINT `fk_exam_scores_exam1` FOREIGN KEY (`exam`) REFERENCES `exam` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_exam_scores_user1` FOREIGN KEY (`user`) REFERENCES `user` (`user_name`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `presentation`
--
ALTER TABLE `presentation`
  ADD CONSTRAINT `fk_presentation_chapter1` FOREIGN KEY (`chapter`) REFERENCES `chapter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_business_unit1` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_user_type1` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
