export interface DeliveryDetailsDisplay {
    id: number;
    restaurantName: string;
    deliveryName: string;
    customerName: string;
    endAddress: string;
    orderId: number;
    createdDateTime: string;
    timeTaken: string;
    totalDistance: number;
    deliveryStatus: string;
    deliveryItems: DeliveryItem[];
}
export interface DeliveryItem {
    itemName: string;
    quantity: number;
  }