CREATE DATABASE  IF NOT EXISTS `temptask18` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `temptask18`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: rdsvd74brlq8tl7tat3ia.mysql.rds.aliyuncs.com    Database: temptask18
-- ------------------------------------------------------
-- Server version	5.6.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='cca8c0fa-0e8c-11e5-ac67-6c92bf0a827f:1-1685766,
d6180e3f-0e8c-11e5-ac67-6c92bf0a814b:1-1774174';

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(12) NOT NULL COMMENT '类目id',
  `name` varchar(128) NOT NULL COMMENT '类目名称',
  `url` varchar(1024) DEFAULT NULL COMMENT '类目链接',
  `parent_id` bigint(12) DEFAULT NULL COMMENT '父类目id',
  `platform_id` int(11) NOT NULL COMMENT '平台id：1：贵农网；2：黔茶商城；3：艾特购；4：苗岭星空',
  PRIMARY KEY (`id`,`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(16) DEFAULT NULL COMMENT '商城item_id',
  `name` varchar(512) DEFAULT NULL COMMENT '商品名称',
  `url` varchar(1024) DEFAULT NULL COMMENT '商品链接',
  `market_price` decimal(14,2) DEFAULT NULL COMMENT '市场价格',
  `sell_price` decimal(14,2) DEFAULT NULL COMMENT '折扣价格',
  `sell_price_max` decimal(14,2) DEFAULT NULL COMMENT '最高价',
  `sell_count` int(11) DEFAULT NULL COMMENT '成交笔数',
  `comment_count` int(11) DEFAULT NULL COMMENT '评论总数',
  `stock` int(11) DEFAULT NULL COMMENT '库存量',
  `cat1_id` bigint(12) DEFAULT NULL COMMENT '一级类目id',
  `cat2_id` bigint(12) DEFAULT NULL COMMENT '二级类目id',
  `cat3_id` bigint(12) DEFAULT NULL COMMENT '三级类目id',
  `shop_id` bigint(12) DEFAULT NULL COMMENT '所属店铺id',
  `platform_id` int(11) DEFAULT NULL COMMENT '平台id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31335 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_price_member`
--

DROP TABLE IF EXISTS `item_price_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_price_member` (
  `item_id` bigint(16) NOT NULL COMMENT 'reference item.item_id',
  `price_common` decimal(14,2) DEFAULT NULL COMMENT '会员普通价',
  `price_pifa` decimal(14,2) DEFAULT NULL COMMENT '批发价',
  `price_gold` decimal(14,2) DEFAULT NULL COMMENT '金牌会员价',
  `price_silver` decimal(14,2) DEFAULT NULL COMMENT '银牌会员价',
  `price_diamond` decimal(14,2) DEFAULT NULL COMMENT '钻石会员价',
  `platform_id` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`item_id`,`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `platform`
--

DROP TABLE IF EXISTS `platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform` (
  `id` int(11) NOT NULL COMMENT '商城id',
  `name` varchar(45) NOT NULL COMMENT '商城名称',
  `index` varchar(512) DEFAULT NULL COMMENT '商城首页'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop` (
  `id` bigint(12) NOT NULL,
  `name` varchar(512) DEFAULT NULL COMMENT '店铺名称',
  `url` varchar(1024) DEFAULT NULL COMMENT '店铺链接',
  `platform_id` int(11) NOT NULL COMMENT '平台id',
  PRIMARY KEY (`id`,`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-07 10:28:17
