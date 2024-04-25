import request from 'axios'

export const getAppList = async (pageNumber: number, pageSize: number): Promise<string[]> => {
  try {
    let uri: string = '/api/app?pageNumber=' + pageNumber + "&pageSize=" + pageSize;
    let response = await request.get(uri, {});
    console.log(response);
    let clientId = response.data.content.map(app=>app.clientId);
    console.log(clientId);
  } catch (error) {
    console.log("got error")
    console.log(error)
    return ["error"];
  }
  return ["not implement"];
}