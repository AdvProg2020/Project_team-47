package model.others;

import model.category.Category;
import model.category.MainCategory;
import model.category.SubCategory;
import model.discount.DiscountCode;
import model.discount.Off;
import model.log.Log;
import model.others.request.Request;
import model.user.User;

import java.util.ArrayList;

public class Sort {
    public static ArrayList<MainCategory> sortMainCategories(String field, String direction, ArrayList<MainCategory> categories) {
        ArrayList<MainCategory> categoriesClone = (ArrayList<MainCategory>) categories.clone();
        return Sort.sortCategory(field, direction, categoriesClone);
    }

    public static ArrayList<SubCategory> sortSubCategories(String field, String direction, ArrayList<SubCategory> categories) {
        ArrayList<SubCategory> categoriesClone = (ArrayList<SubCategory>) categories.clone();
        return Sort.sortCategory(field, direction, categoriesClone);
    }

    private static <E> ArrayList<E> sortCategory(String field, String direction, ArrayList<E> categoriesClone) {
        ArrayList<Category> categories = (ArrayList<Category>) categoriesClone;
        if (field == null || direction == null) {
            return (ArrayList<E>) categories;
        }
        if (field.equals("name")) {
            categories.sort((o1, o2) -> {
                if (direction.startsWith("a")) {
                    return o1.getName().compareTo(o2.getName());
                } else if (direction.startsWith("d")) {
                    return o2.getName().compareTo(o1.getName());
                }
                return 1;
            });
        }
        return (ArrayList<E>) categories;
    }


    public static ArrayList<Product> sortProduct(String field, String direction, ArrayList<Product> products) {
        ArrayList<Product> productsClone = (ArrayList<Product>) products.clone();
        if (field == null || direction == null) {
            sortProductBySeenTime(productsClone, direction);
            return productsClone;
        }
        switch (field) {
            case "name":
                sortProductByName(productsClone, direction);
                break;
            case "score":
                sortProductByScore(productsClone, direction);
                break;
            case "seen-time":
                sortProductBySeenTime(productsClone, direction);
                break;
            case "price":
                sortProductPrice(productsClone, direction);
                break;
        }
        return productsClone;
    }

    private static void sortProductPrice(ArrayList<Product> products, String direction) {
        products.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return Double.compare(o1.getMinimumPrice(), o2.getMinimumPrice());
            } else if (direction.startsWith("d")) {
                return Double.compare(o2.getMinimumPrice(), o1.getMinimumPrice());
            }
            return 1;
        });
    }

    private static void sortProductByName(ArrayList<Product> products, String direction) {
        products.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getName().compareTo(o2.getName());
            } else if (direction.startsWith("d")) {
                return o2.getName().compareTo(o1.getName());
            }
            return 1;
        });
    }

    private static void sortProductByScore(ArrayList<Product> products, String direction) {
        products.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return Double.compare(o1.getScoreAverage(), o2.getScoreAverage());
            } else if (direction.startsWith("d")) {
                return Double.compare(o2.getScoreAverage(), o1.getScoreAverage());
            }
            return 1;
        });
    }

    private static void sortProductBySeenTime(ArrayList<Product> products, String direction) {
        products.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return Integer.compare(o1.getSeenTime(), o2.getSeenTime());
            } else if (direction.startsWith("d")) {
                return Integer.compare(o2.getSeenTime(), o1.getSeenTime());
            }
            return 1;
        });
    }


    public static ArrayList<DiscountCode> sortDiscountCode(String field, String direction, ArrayList<DiscountCode> discountCodes) {
        ArrayList<DiscountCode> discountCodesClone = (ArrayList<DiscountCode>) discountCodes.clone();
        if (field == null || direction == null) {
            return discountCodesClone;
        }
        switch (field) {
            case "start-time":
                sortDiscountWithStartTime(discountCodesClone, direction);
                break;
            case "finish-time":
                sortDiscountWithFinishTime(discountCodesClone, direction);
                break;
            case "percent":
                sortDiscountCodeByPercent(discountCodesClone, direction);
                break;
        }
        return discountCodesClone;
    }

    private static void sortDiscountCodeByPercent(ArrayList<DiscountCode> discountCodes, String direction) {
        discountCodes.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return Integer.compare(o1.getPercent(), o2.getPercent());

            } else if (direction.startsWith("d")) {
                return Integer.compare(o2.getPercent(), o1.getPercent());
            }
            return 1;
        });
    }

    private static void sortDiscountWithStartTime(ArrayList<DiscountCode> discountCodes, String direction) {
        discountCodes.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            } else if (direction.startsWith("d")) {
                return o2.getStartTime().compareTo(o1.getStartTime());
            }
            return 1;
        });
    }

    private static void sortDiscountWithFinishTime(ArrayList<DiscountCode> discountCodes, String direction) {
        discountCodes.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getFinishTime().compareTo(o2.getFinishTime());
            } else if (direction.startsWith("d")) {
                return o2.getFinishTime().compareTo(o1.getFinishTime());
            }
            return 1;
        });
    }


    public static ArrayList<Off> sortOffs(String field, String direction, ArrayList<Off> offs) {
        ArrayList<Off> offsClone = (ArrayList<Off>) offs.clone();
        if (field == null || direction == null) {
            return offsClone;
        }
        switch (field) {
            case "start-time":
                sortOffWithStartTime(offsClone, direction);
                break;
            case "finish-time":
                sortOffWithFinishTime(offsClone, direction);
                break;
            case "percent":
                sortOffByPercent(offsClone, direction);
                break;
        }
        return offsClone;
    }

    private static void sortOffByPercent(ArrayList<Off> offs, String direction) {
        offs.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return Integer.compare(o1.getPercent(), o2.getPercent());

            } else if (direction.startsWith("d")) {
                return Integer.compare(o2.getPercent(), o1.getPercent());
            }
            return 1;
        });
    }

    private static void sortOffWithStartTime(ArrayList<Off> offs, String direction) {
        offs.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            } else if (direction.startsWith("d")) {
                return o2.getStartTime().compareTo(o1.getStartTime());
            }
            return 1;
        });
    }

    private static void sortOffWithFinishTime(ArrayList<Off> offs, String direction) {
        offs.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getFinishTime().compareTo(o2.getFinishTime());
            } else if (direction.startsWith("d")) {
                return o2.getFinishTime().compareTo(o1.getFinishTime());
            }
            return 1;
        });
    }


    public static ArrayList<User> sortUsers(String field, String direction, ArrayList<User> users) {
        ArrayList<User> usersClone = (ArrayList<User>) users.clone();
        if (field == null || direction == null) {
            return usersClone;
        }

        switch (field) {
            case "first-name":
                sortUserByFirstName(usersClone, direction);
                break;
            case "last-name":
                sortUserByLastName(usersClone, direction);
                break;
            case "username":
                sortUserByUserName(usersClone, direction);
                break;
        }
        return usersClone;
    }

    private static void sortUserByFirstName(ArrayList<User> users, String direction) {
        users.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            } else if (direction.startsWith("d")) {
                return o2.getFirstName().compareTo(o1.getFirstName());
            }
            return 1;
        });
    }

    private static void sortUserByLastName(ArrayList<User> users, String direction) {
        users.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getLastName().compareTo(o2.getLastName());
            } else if (direction.startsWith("d")) {
                return o2.getLastName().compareTo(o1.getLastName());
            }
            return 1;
        });
    }

    private static void sortUserByUserName(ArrayList<User> users, String direction) {
        users.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getUsername().compareTo(o2.getUsername());
            } else if (direction.startsWith("d")) {
                return o2.getUsername().compareTo(o1.getUsername());
            }
            return 1;
        });
    }


    public static ArrayList<Request> sortRequest(String field, String direction, ArrayList<Request> requests) {
        ArrayList<Request> requestsClone = (ArrayList<Request>) requests.clone();
        if (direction == null || field == null) {
            return requestsClone;
        }
        switch (field) {
            case "apply-date":
                sortRequestByApplyDate(requestsClone, direction);
                break;
            case "sender-username":
                sortRequestBySenderUsername(requestsClone, direction);
                break;
        }
        return requestsClone;
    }

    private static void sortRequestByApplyDate(ArrayList<Request> requests, String direction) {
        requests.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getApplyDate().compareTo(o2.getApplyDate());
            } else if (direction.startsWith("d")) {
                return o2.getApplyDate().compareTo(o1.getApplyDate());
            }
            return 1;
        });
    }

    private static void sortRequestBySenderUsername(ArrayList<Request> requests, String direction) {
        requests.sort((o1, o2) -> {
            if (direction.startsWith("a")) {
                return o1.getRequestSender().compareTo(o2.getRequestSender());
            } else if (direction.startsWith("d")) {
                return o2.getRequestSender().compareTo(o1.getRequestSender());
            }
            return 1;
        });
    }


    public static ArrayList<Log> sortLogs(String field, String direction, ArrayList<Log> logs) {
        ArrayList<Log> logsClone = (ArrayList<Log>) logs.clone();
        if (direction == null || field == null) {
            return logsClone;
        }
        switch (field) {
            case "money":
                logsClone.sort((o1, o2) -> {
                    if (direction.startsWith("a")) {
                        return Double.compare(o1.getPrice(), o2.getPrice());
                    } else if (direction.startsWith("d")) {
                        return Double.compare(o1.getPrice(), o2.getPrice());
                    }
                    return 1;
                });
        }
        return logsClone;
    }

}
